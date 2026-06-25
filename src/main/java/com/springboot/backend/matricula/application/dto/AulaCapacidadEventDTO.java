package com.springboot.backend.matricula.application.dto;

public record AulaCapacidadEventDTO(
        Long aulaId,
        String nivelYGrado,
        Integer matriculados,
        Integer capacidadMaxima,
        String estado
) {}