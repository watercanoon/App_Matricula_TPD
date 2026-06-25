package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "aula", uniqueConstraints = {
        @UniqueConstraint(name = "uk_aula_grado_seccion", columnNames = {"nivel", "grado", "seccion"})
})
@SQLRestriction("eliminado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nivel", length = 20, nullable = false)
    private String nivel;

    @Column(name = "grado", length = 20, nullable = false)
    private String grado;

    @Column(name = "seccion", length = 5, nullable = false)
    private String seccion;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @Column(name = "alumnos_matriculados", nullable = false)
    private Integer alumnosMatriculados = 0;
}