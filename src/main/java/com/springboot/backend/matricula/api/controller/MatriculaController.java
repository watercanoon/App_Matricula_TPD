package com.springboot.backend.matricula.api.controller;

import com.springboot.backend.matricula.application.dto.MatriculaRequestDTO;
import com.springboot.backend.matricula.application.dto.MatriculaResponseDTO;
import com.springboot.backend.matricula.application.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> registrarMatricula(@Valid @RequestBody MatriculaRequestDTO request) {
        MatriculaResponseDTO response = matriculaService.matricularAlumno(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}