package com.aurasport.Aurasport.features.partido.dto;

public record UpdateResultadoRequest(
        int golesFavor,
        int golesContra
) {}