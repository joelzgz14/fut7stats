package com.fut7stats.backend.features.equipo.dto;

// Este DTO representa los datos necesarios para crear un nuevo equipo.
public record CreateEquipoRequest(String nombreEquipo, String imagenUrl) {
}
