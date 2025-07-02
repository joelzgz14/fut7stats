package com.aurasport.Aurasport.features.partido.mapper;
import com.aurasport.Aurasport.features.partido.dto.PartidoResponse;
import com.aurasport.Aurasport.model.Partido;
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