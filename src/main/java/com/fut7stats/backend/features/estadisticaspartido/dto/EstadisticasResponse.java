package com.fut7stats.backend.features.estadisticaspartido.dto;

// DTO para devolver la estadística creada o actualizada
public record EstadisticasResponse(
        Long id,
        Long partidoId,
        Long jugadorId,
        String apodoJugador,
        int goles,
        int asistencias,
        int tarjetasAmarillas,
        int tarjetasRojas
) {}