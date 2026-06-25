package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.PagoRequestDTO;
import com.springboot.backend.matricula.application.dto.PagoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagoService {
    PagoResponseDTO procesarPago(PagoRequestDTO request);
    Page<PagoResponseDTO> listarHistorialPagos(Pageable pageable);
}