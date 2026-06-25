package com.springboot.backend.matricula.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponseDTO(
        Long reciboId,
        String concepto,
        String alumno,
        BigDecimal montoPagado,
        String metodoPago,
        LocalDateTime fechaPago,
        String estadoCuota
) {}