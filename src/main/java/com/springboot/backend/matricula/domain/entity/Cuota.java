package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "cuota")
@SQLRestriction("eliminado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuota extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cuota_matricula"))
    private Matricula matricula;

    @Column(name = "concepto", length = 100, nullable = false)
    private String concepto;

    @Column(name = "monto", precision = 10, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "estado_pago", length = 20)
    private String estadoPago = "PENDIENTE";
}