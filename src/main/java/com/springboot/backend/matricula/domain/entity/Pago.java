package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "pago")
@SQLRestriction("eliminado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuota_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pago_cuota"))
    private Cuota cuota;

    @Column(name = "monto_pagado", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoPagado;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;

    @Column(name = "numero_operacion", length = 100)
    private String numeroOperacion;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();
}