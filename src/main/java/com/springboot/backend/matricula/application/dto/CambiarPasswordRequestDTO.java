package com.springboot.backend.matricula.application.dto;
import jakarta.validation.constraints.NotBlank;

public record CambiarPasswordRequestDTO(
        @NotBlank(message = "La contraseña actual es obligatoria") String passwordActual,
        @NotBlank(message = "La nueva contraseña es obligatoria") String passwordNueva
) {}