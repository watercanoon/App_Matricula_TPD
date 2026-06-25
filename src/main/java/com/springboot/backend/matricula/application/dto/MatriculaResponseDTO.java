package com.springboot.backend.matricula.application.dto;

import java.time.LocalDateTime;

public record MatriculaResponseDTO(
        Long id,
        Long alumnoId,
        String nombreCompletoAlumno,
        String gradoYSeccion,
        Integer anioAcademico,
        LocalDateTime fechaMatricula,
        String estado
) {}