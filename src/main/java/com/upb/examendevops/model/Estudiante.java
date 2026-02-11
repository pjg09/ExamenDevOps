package com.upb.examendevops.model;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
* Modelo que representa un estudiante
*/
public class Estudiante {

    // Atributos

    @NotBlank(message = "El ID no puede ser nulo")
    @Pattern(regexp = "^\\d{9}$", message = "El ID debe tener exactamente 9 digitos")
    private String id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La carrera no puede estar vacia")
    private String carrera;

    // Constructor vacio para JSON

    public Estudiante() {}

    // Constructor con todos los parametros

    public Estudiante(String id, String nombre, String carrera) {

        setId(id);

        setNombre(nombre);

        setCarrera(carrera);

    }

    // Getters

    public String getId() {

        return id;

    }

    public String getNombre() {

        return nombre;

    }

    public String getCarrera() {

        return carrera;

    }

    // Setters
    
    public void setId(String id) {

        if (id == null || id.isBlank()) {

            throw new IllegalArgumentException("El ID no puede estar vacio");

        }

        if (!id.matches(id)) {

            throw new IllegalArgumentException("El ID debe tener exactamente 9 digitos");

        }

        this.id = id;

    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException("El nombre no puede estar vacio");

        }

        this.nombre = nombre.trim();

    }

    public void setCarrera(String carrera) {

        if (carrera == null || carrera.isBlank()) {

            throw new IllegalArgumentException("La carrera no puede estar vacia");

        }

        this.carrera = carrera.trim();

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estudiante that = (Estudiante) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);

    }
    
}