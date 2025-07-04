package com.fut7stats.backend.features.jugador.dto;

public record JugadorResponse(
        Long id,
        String nombreCompleto,
        String apodo,
        String posicion,
        Long equipoId,
        String nombreEquipo,
        // --- CAMPOS NUEVOS ---
        Long totalGoles,
        Long totalAsistencias
) {
}
