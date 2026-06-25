package com.springboot.backend.matricula.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PagoRequestDTO(
        @NotNull(message = "El ID de la cuota es obligatorio")
        Long cuotaId,

        @NotBlank(message = "El método de pago es obligatorio")
        String metodoPago,

        String numeroOperacion
) {}