package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "matricula", uniqueConstraints = {
        @UniqueConstraint(name = "uk_matricula_alumno_anio", columnNames = {"alumno_id", "anio_academico"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Matricula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false, foreignKey = @ForeignKey(name = "fk_matricula_aula"))
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false, foreignKey = @ForeignKey(name = "fk_matricula_alumno"))
    private Alumno alumno;

    @Column(name = "anio_academico", nullable = false)
    private Integer anioAcademico;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDateTime fechaMatricula = LocalDateTime.now();
}