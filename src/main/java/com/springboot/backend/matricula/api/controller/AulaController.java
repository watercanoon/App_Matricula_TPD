package com.springboot.backend.matricula.api.controller;

import com.springboot.backend.matricula.application.dto.AulaDTO;
import com.springboot.backend.matricula.application.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDTO>> listarTodas() {
        return ResponseEntity.ok(aulaService.listarAulas());
    }
}