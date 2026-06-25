package com.springboot.backend.matricula.shared.exception;

import com.springboot.backend.matricula.domain.exception.CapacidadExcedidaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de nuestra Regla de Negocio Crítica (Conflicto 409)
    @ExceptionHandler(CapacidadExcedidaException.class)
    public ResponseEntity<Map<String, Object>> handleCapacidadExcedida(CapacidadExcedidaException ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.CONFLICT.value());
        errorInfo.put("error", "Conflicto de Regla de Negocio");
        errorInfo.put("message", ex.getMessage());
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    // 2. Manejo de errores de validación de los DTOs (@NotNull, @Positive) (Bad Request 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.BAD_REQUEST.value());
        errorInfo.put("error", "Error de Validación en los Datos Enviados");
        errorInfo.put("detalles", fieldErrors);
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    // 3. Manejo genérico para evitar que se caiga la app ante errores no previstos (Internal Error 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.put("error", "Error Interno del Servidor");
        errorInfo.put("message", ex.getMessage());
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.UNAUTHORIZED.value());
        errorInfo.put("error", "No Autorizado");
        errorInfo.put("message", "Usuario o contraseña incorrectos");
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.BAD_REQUEST.value());
        errorInfo.put("error", "Operación Inválida");
        errorInfo.put("message", ex.getMessage());
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", HttpStatus.CONFLICT.value());
        errorInfo.put("error", "Violación de Integridad de Datos");
        errorInfo.put("message", "No se puede completar la operación porque el registro está siendo utilizado por otros módulos del sistema.");
        errorInfo.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }
}