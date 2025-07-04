package com.fut7stats.backend.features.partido.dto;

import java.time.OffsetDateTime;

// DTO para recibir los datos necesarios para crear un partido.
public record CreatePartidoRequest(
        String rival,
        OffsetDateTime fechaPartido,
        String lugar,
        Long equipoId
) {
}