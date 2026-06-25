package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.MatriculaRequestDTO;
import com.springboot.backend.matricula.application.dto.MatriculaResponseDTO;

public interface MatriculaService {
    MatriculaResponseDTO matricularAlumno(MatriculaRequestDTO request);
}