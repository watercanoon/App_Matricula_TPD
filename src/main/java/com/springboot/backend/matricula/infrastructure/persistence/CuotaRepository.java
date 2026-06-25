package com.springboot.backend.matricula.infrastructure.persistence;

import com.springboot.backend.matricula.domain.entity.Cuota;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    // Bloqueo Pesimista para evitar doble pago simultáneo
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cuota c WHERE c.id = :id AND c.eliminado = false")
    Optional<Cuota> findByIdWithLock(@Param("id") Long id);
}