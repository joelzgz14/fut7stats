package com.aurasport.Aurasport.features.jugador.dto;

public record CreateJugadorRequest(
        String nombreCompleto,
        String apodo,
        String posicion,
        Long equipoId // Necesitamos saber a qué equipo pertenece
) {
}