package com.springboot.backend.matricula.api.controller;

import com.springboot.backend.matricula.application.dto.CambiarPasswordRequestDTO;
import com.springboot.backend.matricula.application.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PutMapping("/me/password")
    public ResponseEntity<String> cambiarMiPassword(
            @Valid @RequestBody CambiarPasswordRequestDTO request,
            Authentication authentication) { // Spring inyecta automáticamente el usuario del JWT aquí

        String username = authentication.getName();
        usuarioService.cambiarContrasena(username, request);

        return ResponseEntity.ok("Contraseña actualizada exitosamente");
    }
}