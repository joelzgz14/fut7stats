package com.aurasport.Aurasport.features.jugador.dto;

public record JugadorStatsDTO(Long goles, Long asistencias) {
    public JugadorStatsDTO {
        // Aseguramos que si no hay stats, los valores sean 0 en lugar de null.
        if (goles == null) goles = 0L;
        if (asistencias == null) asistencias = 0L;
    }
}
