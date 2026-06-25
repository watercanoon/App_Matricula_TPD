package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "alumno")
@SQLRestriction("eliminado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumno extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni", length = 8, nullable = false, unique = true)
    private String dni;

    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
}