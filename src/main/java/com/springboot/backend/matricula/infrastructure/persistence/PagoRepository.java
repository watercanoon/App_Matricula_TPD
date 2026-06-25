package com.springboot.backend.matricula.infrastructure.persistence;

import com.springboot.backend.matricula.domain.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {}
