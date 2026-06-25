package com.springboot.backend.matricula.infrastructure.persistence;

import com.springboot.backend.matricula.domain.entity.Aula;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    // Bloqueo Pesimista para Operaciones Críticas (SELECT FOR UPDATE)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Aula a WHERE a.id = :id AND a.eliminado = false")
    Optional<Aula> findByIdWithLock(@Param("id") Long id);
}