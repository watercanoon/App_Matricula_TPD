package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.AulaCapacidadEventDTO;
import com.springboot.backend.matricula.application.dto.MatriculaRequestDTO;
import com.springboot.backend.matricula.application.dto.MatriculaResponseDTO;
import com.springboot.backend.matricula.domain.entity.Alumno;
import com.springboot.backend.matricula.domain.entity.Aula;
import com.springboot.backend.matricula.domain.entity.Cuota;
import com.springboot.backend.matricula.domain.entity.Matricula;
import com.springboot.backend.matricula.domain.exception.CapacidadExcedidaException;
import com.springboot.backend.matricula.infrastructure.persistence.AlumnoRepository;
import com.springboot.backend.matricula.infrastructure.persistence.AulaRepository;
import com.springboot.backend.matricula.infrastructure.persistence.CuotaRepository;
import com.springboot.backend.matricula.infrastructure.persistence.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final AulaRepository aulaRepository;
    private final MatriculaRepository matriculaRepository;
    private final AlumnoRepository alumnoRepository;
    private final CuotaRepository cuotaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class) // Todo o nada. Si falla la cuota, hace Rollback automático.
    public MatriculaResponseDTO matricularAlumno(MatriculaRequestDTO request) {
        log.info("Iniciando matrícula para Alumno ID: {} en Aula ID: {}", request.alumnoId(), request.aulaId());

        // 1. BLOQUEO PESIMISTA: SELECT ... FOR UPDATE
        Aula aula = aulaRepository.findByIdWithLock(request.aulaId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        // 2. Validar regla de negocio crítica
        if (aula.getAlumnosMatriculados() >= aula.getCapacidadMaxima()) {
            log.error("Capacidad excedida en el aula ID: {}", aula.getId());
            throw new CapacidadExcedidaException("El aula ha alcanzado su capacidad máxima permitida de " + aula.getCapacidadMaxima() + " alumnos.");
        }

        // 3. Validar si ya está matriculado este año
        boolean yaMatriculado = matriculaRepository.existsByAlumnoIdAndAnioAcademicoAndEliminadoFalse(
                request.alumnoId(), request.anioAcademico());
        if (yaMatriculado) {
            throw new RuntimeException("El alumno ya se encuentra matriculado para el año " + request.anioAcademico());
        }

        // 4. Buscar y Asignar Alumno Real
        Alumno alumno = alumnoRepository.findById(request.alumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado en la base de datos"));

        Matricula matricula = new Matricula();
        matricula.setAula(aula);
        matricula.setAlumno(alumno);
        matricula.setAnioAcademico(request.anioAcademico());

        // 5. Guardar Matrícula
        matricula = matriculaRepository.save(matricula);

        // =========================================================
        // 5.1 Generar Cuota de Matrícula automáticamente
        // =========================================================
        Cuota cuotaMatricula = new Cuota();
        cuotaMatricula.setMatricula(matricula);
        cuotaMatricula.setConcepto("MATRÍCULA " + request.anioAcademico());

        // En un sistema real, este monto vendría de una tabla "Concepto" o "Configuración".
        // Lo definimos estático para el MVP transaccional.
        cuotaMatricula.setMonto(new BigDecimal("350.00"));

        // Le damos 7 días para pagar antes de que venza
        cuotaMatricula.setFechaVencimiento(LocalDate.now().plusDays(7));

        cuotaRepository.save(cuotaMatricula);
        log.info("Cuota de matrícula generada exitosamente con ID: {}", cuotaMatricula.getId());
        // =========================================================

        // 6. Actualizar contadores del Aula
        aula.setAlumnosMatriculados(aula.getAlumnosMatriculados() + 1);
        aulaRepository.save(aula);

        log.info("Matrícula exitosa con ID: {}", matricula.getId());

        // =========================================================
        // 7. EMITIR EVENTO WEBSOCKET EN TIEMPO REAL
        // =========================================================
        AulaCapacidadEventDTO evento = new AulaCapacidadEventDTO(
                aula.getId(),
                aula.getGrado() + " " + aula.getSeccion(),
                aula.getAlumnosMatriculados(),
                aula.getCapacidadMaxima(),
                aula.getAlumnosMatriculados() >= aula.getCapacidadMaxima() ? "LLENO" : "DISPONIBLE"
        );
        messagingTemplate.convertAndSend("/topic/aulas", evento);

        return new MatriculaResponseDTO(
                matricula.getId(),
                alumno.getId(),
                alumno.getNombres() + " " + alumno.getApellidos(),
                aula.getGrado() + " " + aula.getSeccion(),
                matricula.getAnioAcademico(),
                matricula.getFechaMatricula(),
                matricula.getEstado()
        );
    }
}