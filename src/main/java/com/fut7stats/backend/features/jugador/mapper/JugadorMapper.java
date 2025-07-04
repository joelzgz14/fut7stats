package com.fut7stats.backend.features.jugador.mapper;

import com.fut7stats.backend.features.jugador.dto.JugadorResponse;
import com.fut7stats.backend.features.jugador.dto.JugadorStatsDTO;
import com.fut7stats.backend.model.Jugador;
import org.springframework.stereotype.Component;

@Component
public class JugadorMapper {

    // El método ahora acepta un Jugador y sus estadísticas
    public JugadorResponse toResponse(Jugador jugador, JugadorStatsDTO stats) {
        if (jugador == null) {
            return null;
        }
        // Usamos los datos de ambos objetos para construir la respuesta completa
        return new JugadorResponse(
                jugador.getId(),
                jugador.getNombreCompleto(),
                jugador.getApodo(),
                jugador.getPosicion(),
                jugador.getEquipo().getId(),
                jugador.getEquipo().getNombreEquipo(),
                stats.goles(),       // <-- Usamos el parámetro de stats
                stats.asistencias()  // <-- Usamos el parámetro de stats
        );
    }
}