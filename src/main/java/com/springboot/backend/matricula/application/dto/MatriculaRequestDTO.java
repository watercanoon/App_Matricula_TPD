package com.springboot.backend.matricula.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatriculaRequestDTO(
        @NotNull(message = "El ID del alumno es obligatorio")
        Long alumnoId,

        @NotNull(message = "El ID del aula es obligatorio")
        Long aulaId,

        @NotNull(message = "El año académico es obligatorio")
        @Positive(message = "El año académico debe ser un número positivo")
        Integer anioAcademico
) {}