package com.upb.examendevops.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "Error de validación en los datos enviados");
        
        Map<String, String> errores = new HashMap<>();
        
        // Procesar cada error de validación

        ex.getBindingResult().getAllErrors().forEach(error -> {

            try {

                String campo = ((FieldError) error).getField();
                String mensaje = error.getDefaultMessage();

                errores.put(campo, mensaje);

            } catch (Exception e) {

                // Si hay algún error al procesar, agregar un error genérico

                errores.put("error", error.getDefaultMessage());
                
            }
        });
        
        respuesta.put("errores", errores);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }

    /**
     * Maneja errores de deserialización JSON (cuando los setters lanzan excepciones).
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorDeserializacion(HttpMessageNotReadableException ex) {
        
        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");
        
        // Extraer el mensaje específico de la causa raíz
        
        String mensaje = "Datos inválidos en el cuerpo de la solicitud";
        Throwable causa = ex.getCause();
        
        if (causa != null) {

            Throwable causaRaiz = causa;

            while (causaRaiz.getCause() != null) {

                causaRaiz = causaRaiz.getCause();

            }
            
            if (causaRaiz instanceof IllegalArgumentException) {

                mensaje = causaRaiz.getMessage();

            }
        }
        
        error.put("mensaje", mensaje);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
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

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");
        error.put("mensaje", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

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

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("mensaje", "Ha ocurrido un error inesperado");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
    
}