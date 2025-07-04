package com.fut7stats.backend.features.partido.dto;

public record UpdateResultadoRequest(
        int golesFavor,
        int golesContra
) {}