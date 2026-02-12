package com.upb.examendevops.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para toda la aplicacion
 * Captura y formatea las respuestas de error de manera consistente
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de validacion de Jakarta Bean Validation
     * Se dispara cuando @Valid falla en el controller
     * 
     * @param ex La excepcion de validacion
     * @return ResponseEntity con detalles del error y codigo 400
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresDeValidacion(MethodArgumentNotValidException ex) {

        Map<String, Object> errores = new HashMap<>();

        errores.put("timestamp", LocalDateTime.now());
        errores.put("status", HttpStatus.BAD_REQUEST.value());

        Map <String, String> camposConError = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();

            camposConError.put(campo, mensaje);

        });

        errores.put("errores", camposConError);
        errores.put("mensaje", "Error de validacion en los datos enviados");

        return ResponseEntity.badRequest().body(errores);

    }

    /**
     * Maneja IllegalArgumentException (lanzadas por el Service y el Modelo)
     * 
     * @param ex La excepcion lanzada
     * @return ResponseEntity con detalles del error y codigo 400
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarArgumentosIlegales(IllegalArgumentException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");
        error.put("mensaje", ex.getMessage());

        return ResponseEntity.badRequest().body(error);

    }

    /**
     * Maneja cualquier excepcion no capturada especificamente
     * 
     * @param ex La excepcion
     * @return ResponseEntity con detalles del erro y codigo 500
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGenerico(Exception ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("mensaje", "Ha ocurrido un error inesperado");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
    
}