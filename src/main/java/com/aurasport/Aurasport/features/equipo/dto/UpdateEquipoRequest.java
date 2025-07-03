package com.aurasport.Aurasport.features.equipo.dto;

import java.util.Optional;

// Este DTO puede manejar cualquier actualización: solo nombre, solo imagen, o ambas.
public record UpdateEquipoRequest(
        Optional<String> nombreEquipo,
        Optional<String> imagenUrl
) {}