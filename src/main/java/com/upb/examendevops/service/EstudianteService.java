package com.upb.examendevops.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.upb.examendevops.model.Estudiante;

/**
 * Servicio que maneja la logica de negocio para estudiantes
 * Almacena los datos en memoria usando un Map
 */

@Service
public class EstudianteService {
    
    // Map para almacenar estudiantes en memoria: key=id, value=Estudiante
    private final Map<String, Estudiante> estudiantes = new ConcurrentHashMap<>();

}