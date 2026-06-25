package com.springboot.backend.matricula.api.controller;

import com.springboot.backend.matricula.application.dto.PagoRequestDTO;
import com.springboot.backend.matricula.application.dto.PagoResponseDTO;
import com.springboot.backend.matricula.application.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> realizarPago(@Valid @RequestBody PagoRequestDTO request) {
        PagoResponseDTO response = pagoService.procesarPago(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PagoResponseDTO>> obtenerHistorial(
            @PageableDefault(size = 10, sort = "fechaPago") Pageable pageable) {
        return ResponseEntity.ok(pagoService.listarHistorialPagos(pageable));
    }
}