package com.fut7stats.backend.features.estadisticaspartido.mapper;

import com.fut7stats.backend.features.estadisticaspartido.dto.EstadisticasResponse;
import com.fut7stats.backend.model.EstadisticasPartido;
import org.springframework.stereotype.Component;

@Component
public class EstadisticasMapper {
    public EstadisticasResponse toResponse(EstadisticasPartido e) {
        return new EstadisticasResponse(e.getId(), e.getPartido().getId(), e.getJugador().getId(), e.getJugador().getApodo(), e.getGoles(), e.getAsistencias(), e.getTarjetasAmarillas(), e.getTarjetasRojas());
    }
}
