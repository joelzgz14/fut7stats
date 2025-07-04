package com.fut7stats.backend.features.partido.mapper;
import com.fut7stats.backend.features.partido.dto.PartidoResponse;
import com.fut7stats.backend.model.Partido;
import org.springframework.stereotype.Component;

@Component
public class PartidoMapper {

    public PartidoResponse toResponse(Partido partido) {
        if (partido == null) {
            return null;
        }
        return new PartidoResponse(
                partido.getId(),
                partido.getRival(),
                partido.getFechaPartido(),
                partido.getLugar(),
                partido.getGolesFavor(),
                partido.getGolesContra(),
                partido.getEquipo().getId()
        );
    }
}