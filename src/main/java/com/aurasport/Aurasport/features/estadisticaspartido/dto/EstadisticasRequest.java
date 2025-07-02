package com.aurasport.Aurasport.features.estadisticaspartido.dto;

// Usaremos esta clase para añadir un bloque de estadísticas
public record EstadisticasRequest(
        Long partidoId,
        Long jugadorId,
        int goles,
        int asistencias,
        int tarjetasAmarillas,
        int tarjetasRojas
) {}
