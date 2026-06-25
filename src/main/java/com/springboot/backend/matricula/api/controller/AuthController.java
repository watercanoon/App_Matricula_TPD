package com.springboot.backend.matricula.api.controller;

import com.springboot.backend.matricula.application.dto.AuthRequestDTO;
import com.springboot.backend.matricula.application.dto.AuthResponseDTO;
import com.springboot.backend.matricula.domain.entity.Usuario;
import com.springboot.backend.matricula.infrastructure.persistence.UsuarioRepository;
import com.springboot.backend.matricula.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        // Esto verifica encriptación y lanza error si es inválido
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // Si pasa, generamos el Token
        Usuario user = usuarioRepository.findByUsername(request.username()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(jwtToken));
    }
}