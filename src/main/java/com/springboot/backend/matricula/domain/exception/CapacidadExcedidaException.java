package com.springboot.backend.matricula.domain.exception;

public class CapacidadExcedidaException extends RuntimeException {
    public CapacidadExcedidaException(String message) {
        super(message);
    }
}