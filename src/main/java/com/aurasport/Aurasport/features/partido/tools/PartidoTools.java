package com.aurasport.Aurasport.features.partido.tools;

import com.aurasport.Aurasport.features.equipo.repository.EquipoRepository;
import com.aurasport.Aurasport.features.partido.dto.CreatePartidoRequest;
import com.aurasport.Aurasport.features.partido.dto.UpdateResultadoRequest;
import com.aurasport.Aurasport.features.partido.repository.PartidoRepository;
import com.aurasport.Aurasport.features.partido.service.PartidoService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class PartidoTools {

    private final PartidoService partidoService;
    private final EquipoRepository equipoRepository;
    private final PartidoRepository partidoRepository;

    public PartidoTools(PartidoService partidoService, EquipoRepository equipoRepository,
                        PartidoRepository partidoRepository) {
        this.partidoService = partidoService;
        this.equipoRepository = equipoRepository;
        this.partidoRepository = partidoRepository;
    }

    @Tool("Crea un nuevo partido para un equipo contra un rival en una fecha específica.")
    public String crearPartido(
            @P("Nombre del equipo local, el nuestro.") String nombreEquipo,
            @P("Nombre del equipo rival.") String nombreRival,
            @P("Fecha y hora del partido en formato ISO, por ejemplo: 2025-07-03T20:00:00Z") String fecha,
            @P("Lugar donde se jugará el partido.") String lugar
    ) {
        System.out.println("LOG: Herramienta 'crearPartido' invocada.");
        Long equipoId = equipoRepository.findByNombreEquipo(nombreEquipo)
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo '" + nombreEquipo + "'."))
                .getId();

        OffsetDateTime fechaPartido = OffsetDateTime.parse(fecha);
        CreatePartidoRequest request = new CreatePartidoRequest(nombreRival, fechaPartido, lugar, equipoId);
        var partidoCreado = partidoService.create(request);

        return "Partido creado con éxito. " + nombreEquipo + " vs " + nombreRival + " el " + partidoCreado.fechaPartido();
    }

    @Tool("Actualiza el resultado final de un partido ya existente.")
    public String actualizarResultadoPartido(
            @P("Nombre del equipo rival del partido que se quiere actualizar.") String nombreRival,
            @P("Goles marcados por nuestro equipo.") int golesFavor,
            @P("Goles marcados por el equipo rival.") int golesContra
    ) {
        System.out.println("LOG: Herramienta 'actualizarResultadoPartido' invocada.");

        // Buscamos el último partido contra ese rival
        Long partidoId = partidoRepository.findFirstByRivalIgnoreCaseOrderByFechaPartidoDesc(nombreRival)
                .orElseThrow(() -> new RuntimeException("No se encontró un partido contra '" + nombreRival + "'."))
                .getId();

        UpdateResultadoRequest request = new UpdateResultadoRequest(golesFavor, golesContra);
        partidoService.actualizarResultado(partidoId, request);

        return "Resultado del partido contra " + nombreRival + " actualizado a " + golesFavor + " - " + golesContra + ".";
    }
}