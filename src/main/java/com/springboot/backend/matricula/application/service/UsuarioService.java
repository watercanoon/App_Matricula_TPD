package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.CambiarPasswordRequestDTO;
import com.springboot.backend.matricula.domain.entity.Usuario;
import com.springboot.backend.matricula.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void cambiarContrasena(String username, CambiarPasswordRequestDTO request) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que la contraseña actual ingresada coincida con la encriptada
        if (!passwordEncoder.matches(request.passwordActual(), usuario.getPassword())) {
            throw new IllegalStateException("La contraseña actual ingresada es incorrecta");
        }

        // Validar que no ponga la misma contraseña
        if (passwordEncoder.matches(request.passwordNueva(), usuario.getPassword())) {
            throw new IllegalStateException("La nueva contraseña no puede ser igual a la anterior");
        }

        // Encriptar y guardar
        usuario.setPassword(passwordEncoder.encode(request.passwordNueva()));
        usuarioRepository.save(usuario);
    }
}