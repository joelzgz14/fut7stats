package com.fut7stats.backend.features.jugador.dto;

import java.util.Optional;

public record UpdateJugadorRequest(
        Optional<String> nuevoNombreCompleto,
        Optional<String> nuevoApodo,
        Optional<String> nuevaPosicion
) {}
