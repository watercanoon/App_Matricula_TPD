package com.springboot.backend.matricula.infrastructure.persistence;

import com.springboot.backend.matricula.domain.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByAlumnoIdAndAnioAcademicoAndEliminadoFalse(Long alumnoId, Integer anioAcademico);
}