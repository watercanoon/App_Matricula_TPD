package com.springboot.backend.matricula.application.dto;
public record AulaDTO(Long id, String nivel, String grado, String seccion, Integer matriculados, Integer capacidad) {}