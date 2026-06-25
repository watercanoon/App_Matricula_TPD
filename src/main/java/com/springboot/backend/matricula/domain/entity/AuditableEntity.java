package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Activa la escucha de eventos de auditoría
public abstract class AuditableEntity {

    @Column(name = "estado", length = 20)
    private String estado = "ACTIVO";

    @Column(name = "eliminado")
    private Boolean eliminado = false;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @CreatedBy
    @Column(name = "usuario_creacion", length = 50, updatable = false)
    private String usuarioCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @LastModifiedBy
    @Column(name = "usuario_modificacion", length = 50)
    private String usuarioModificacion;

    @Version
    @Column(name = "version")
    private Long version = 0L;
}