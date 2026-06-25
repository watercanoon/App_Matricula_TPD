package com.springboot.backend.matricula.infrastructure.persistence;

import com.springboot.backend.matricula.domain.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}