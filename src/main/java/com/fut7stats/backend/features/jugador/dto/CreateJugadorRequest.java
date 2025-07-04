package com.fut7stats.backend.features.jugador.dto;

public record CreateJugadorRequest(
        String nombreCompleto,
        String apodo,
        String posicion,
        Long equipoId // Necesitamos saber a qué equipo pertenece
) {
}