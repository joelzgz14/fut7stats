package com.aurasport.Aurasport.features.equipo.mapper;

import com.aurasport.Aurasport.features.equipo.dto.CreateEquipoRequest;
import com.aurasport.Aurasport.features.equipo.dto.EquipoResponse;
import com.aurasport.Aurasport.model.Equipo;
import org.springframework.stereotype.Component;

@Component // Le decimos a Spring que esta clase es un componente gestionado
public class EquipoMapper {

    public Equipo toEntity(CreateEquipoRequest request) {
        if (request == null) {
            return null;
        }
        Equipo equipo = new Equipo();
        equipo.setNombreEquipo(request.nombreEquipo());
        equipo.setImagenUrl(request.imagenUrl()); //
        return equipo;
    }

    public EquipoResponse toResponse(Equipo equipo) {
        if (equipo == null) {
            return null;
        }
        return new EquipoResponse(equipo.getId(), equipo.getNombreEquipo(), equipo.getImagenUrl());
    }
}