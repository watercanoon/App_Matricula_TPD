package com.springboot.backend.matricula.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker en memoria para enviar mensajes a los clientes suscritos a "/topic"
        config.enableSimpleBroker("/topic");
        // Prefijo para los mensajes que el cliente envía al servidor (si fuera necesario)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // El endpoint principal al que se conectará el Frontend (ej. React/Angular)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*"); // En producción se restringe al dominio del Frontend
        // .withSockJS(); // Opcional: Fallback si el navegador no soporta WebSockets puros
    }
}