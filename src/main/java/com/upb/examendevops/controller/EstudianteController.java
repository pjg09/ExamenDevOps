package com.upb.examendevops.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upb.examendevops.model.Estudiante;
import com.upb.examendevops.service.EstudianteService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar estudiantes
 * Expone endpoints para crear y listar estudiantes
 */

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {

        this.estudianteService = estudianteService;

    }

    /**
     * Crea un nuevo estudiante
     * POST /estudiantes
     * 
     * @param estudiante El estudiante a crear (validado automaticamente)
     * @return ResponseEntity con el estudiante creado y codigo 201
     */

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@Valid @RequestBody Estudiante estudiante) {

        Estudiante estudianteGuardado = estudianteService.guardar(estudiante);

        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteGuardado);

    }

    /**
     * Obtiene todos los estudiantes registrados
     * GET /estudiantes
     * 
     * @return ResponseEntity con la lista de estudiantes y codigo 200
     */

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodos() {

        List<Estudiante> estudiantes = estudianteService.obtenerTodos();

        return ResponseEntity.ok(estudiantes);

    }
    
}