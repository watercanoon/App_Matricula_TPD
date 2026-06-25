package com.springboot.backend.matricula.infrastructure.config;

import com.springboot.backend.matricula.infrastructure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF porque nuestra API es Stateless (no usa sesiones de navegador)
                .csrf(AbstractHttpConfigurer::disable)

                // Configuramos las reglas de autorización de rutas
                .authorizeHttpRequests(auth -> auth
                        // ¡LA CLAVE ESTÁ AQUÍ! Permitimos acceso total a los endpoints de auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Cualquier otra petición (como /matriculas) requiere estar autenticado
                        .anyRequest().authenticated()
                )

                // Le indicamos a Spring que no guarde el estado de la sesión (usaremos JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Registramos nuestro proveedor y nuestro filtro JWT
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}