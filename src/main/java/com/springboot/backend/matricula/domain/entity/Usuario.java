package com.springboot.backend.matricula.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.SQLRestriction;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@SQLRestriction("eliminado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Rol rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Le agregamos el prefijo "ROLE_" que exige Spring Security por convención
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return getEstado().equals("ACTIVO"); }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return !getEliminado(); }
}