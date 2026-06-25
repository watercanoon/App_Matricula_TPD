package com.springboot.backend.matricula.application.service;

import com.springboot.backend.matricula.application.dto.AulaDTO;
import com.springboot.backend.matricula.infrastructure.persistence.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;

    // Al agregar esta anotación, la primera vez va a MySQL, las siguientes va a Redis.
    @Cacheable(value = "listaAulas")
    public List<AulaDTO> listarAulas() {
        return aulaRepository.findAll().stream()
                .map(aula -> new AulaDTO(aula.getId(), aula.getNivel(), aula.getGrado(), aula.getSeccion(), aula.getAlumnosMatriculados(), aula.getCapacidadMaxima()))
                .toList();
    }
}