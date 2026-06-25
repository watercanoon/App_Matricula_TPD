package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.PagoRequestDTO;
import com.springboot.backend.matricula.application.dto.PagoResponseDTO;
import com.springboot.backend.matricula.domain.entity.Cuota;
import com.springboot.backend.matricula.domain.entity.Pago;
import com.springboot.backend.matricula.infrastructure.persistence.CuotaRepository;
import com.springboot.backend.matricula.infrastructure.persistence.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final CuotaRepository cuotaRepository;
    private final PagoRepository pagoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PagoResponseDTO procesarPago(PagoRequestDTO request) {
        log.info("Iniciando proceso de pago para Cuota ID: {}", request.cuotaId());

        // 1. SELECT FOR UPDATE: Bloqueamos la cuota para que nadie más la toque
        Cuota cuota = cuotaRepository.findByIdWithLock(request.cuotaId())
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

        // 2. Validar que no esté pagada ya
        if (!"PENDIENTE".equals(cuota.getEstadoPago())) {
            log.warn("Intento de pago de cuota ya procesada. ID: {}", cuota.getId());
            throw new IllegalStateException("Esta cuota ya ha sido pagada o anulada.");
        }

        // 3. Registrar el Pago (Recibo)
        Pago pago = new Pago();
        pago.setCuota(cuota);
        pago.setMontoPagado(cuota.getMonto()); // En este MVP asumimos que paga el monto completo
        pago.setMetodoPago(request.metodoPago());
        pago.setNumeroOperacion(request.numeroOperacion());

        pago = pagoRepository.save(pago);

        // 4. Cambiar el estado de la Cuota a PAGADO
        cuota.setEstadoPago("PAGADO");
        cuotaRepository.save(cuota);

        log.info("Pago procesado exitosamente. Recibo ID: {}", pago.getId());

        // 5. Construir y retornar el recibo
        String nombreAlumno = cuota.getMatricula().getAlumno().getNombres() + " " +
                cuota.getMatricula().getAlumno().getApellidos();

        return new PagoResponseDTO(
                pago.getId(),
                cuota.getConcepto(),
                nombreAlumno,
                pago.getMontoPagado(),
                pago.getMetodoPago(),
                pago.getFechaPago(),
                cuota.getEstadoPago()
        );
    }

    @Override
    @Transactional(readOnly = true) // Optimiza la transacción solo para lectura
    public Page<PagoResponseDTO> listarHistorialPagos(Pageable pageable) {
        return pagoRepository.findAll(pageable)
                .map(pago -> new PagoResponseDTO(
                        pago.getId(),
                        pago.getCuota().getConcepto(),
                        pago.getCuota().getMatricula().getAlumno().getNombres() + " " + pago.getCuota().getMatricula().getAlumno().getApellidos(),
                        pago.getMontoPagado(),
                        pago.getMetodoPago(),
                        pago.getFechaPago(),
                        pago.getCuota().getEstadoPago()
                ));
    }
}