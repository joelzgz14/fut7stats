package com.fut7stats.backend.features.equipo.dto;

// Este DTO representa la información de un equipo que devolvemos al cliente.
public record EquipoResponse(Long id, String nombreEquipo, String imagenUrl) {
}
