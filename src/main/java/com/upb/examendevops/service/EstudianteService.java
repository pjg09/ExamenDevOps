package com.upb.examendevops.service;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * Guarda un nuevo estudiante
     * Valida que el ID sea unico antes de guardar
     * 
     * @param estudiante El estudiante a guardar
     * @return El estudiante guardado
     * @throws IllegalArgumentException Si el ID ya existe
     */

    public Estudiante guardar(Estudiante estudiante) {

        if (estudiante == null) {

            throw new IllegalArgumentException("El estudiante no puede ser nulo");

        }

        String id = estudiante.getId();

        if (estudiantes.containsKey(id)) {

            throw new IllegalArgumentException("Ya existe un estudiante con el ID: " + id);

        }

        estudiantes.put(id, estudiante);
        return estudiante;

    }

    /**
     * Obtiene todos los estudiantes registrados
     * 
     * @return Lista de todos los estudiantes
     */

    public List<Estudiante> obtenerTodos() {

        return new ArrayList<>(estudiantes.values());

    }

    /**
     * Obtiene un estudiante por su ID
     * 
     * @param id El ID del estudiante a buscar
     * @return El estudiante encontrado, o null si no existe
     */

    public Estudiante obtenerPorId(String id) {

        return estudiantes.get(id);

    }

    /**
     * Verifica si existe un estudiante con el ID dado
     * 
     * @param id El ID a verificar
     * @return true si existe un estudiante con ese ID, false en caso contrario
     */

    public boolean existePorId(String id) {

        return estudiantes.containsKey(id);

    }

    /**
     * Limpia todos los estudiantes registrados (para pruebas)
     */

    public void limpiar() {

        estudiantes.clear();
    
    }

    /**
     * Obtiene la cantidad total de estudiantes registrados
     * 
     * @return Numero de estudiantes
     */

    public int contarEstudiantes() {

        return estudiantes.size();

    }

}