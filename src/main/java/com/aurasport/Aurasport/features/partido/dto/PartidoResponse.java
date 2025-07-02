package com.aurasport.Aurasport.features.partido.dto;

import java.time.OffsetDateTime;

// DTO para devolver la información de un partido ya creado.
public record PartidoResponse(
        Long id,
        String rival,
        OffsetDateTime fechaPartido,
        String lugar,
        int golesFavor,
        int golesContra,
        Long equipoId
) {
}