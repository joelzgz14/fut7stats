package com.aurasport.Aurasport.features.estadisticaspartido.tools;

import com.aurasport.Aurasport.features.estadisticaspartido.dto.EstadisticasRequest;
import com.aurasport.Aurasport.features.estadisticaspartido.service.EstadisticasService;
import com.aurasport.Aurasport.features.jugador.repository.JugadorRepository;
import com.aurasport.Aurasport.features.partido.repository.PartidoRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class EstadisticasTools {

    private final EstadisticasService estadisticasService;
    private final JugadorRepository jugadorRepository;
    private final PartidoRepository partidoRepository;

    public EstadisticasTools(EstadisticasService es, JugadorRepository jr, PartidoRepository pr) {
        this.estadisticasService = es;
        this.jugadorRepository = jr;
        this.partidoRepository = pr;
    }

    @Tool("Registra las estadísticas de un jugador en un partido, identificado por el equipo rival.")
    public String anadirEstadisticas(
            @P("Apodo del jugador que realizó las acciones.") String apodoJugador,
            @P("Nombre del equipo rival del partido.") String nombreRival,
            @P("Número de goles marcados. Por defecto es 0 si no se menciona.") int goles,
            @P("Número de asistencias dadas. Por defecto es 0 si no se menciona.") int asistencias
    ) {
        System.out.println("LOG: Herramienta 'añadirEstadisticas' invocada para " + apodoJugador);

        Long jugadorId = jugadorRepository.findFirstByApodoIgnoreCase(apodoJugador)
                .orElseThrow(() -> new RuntimeException("No se encontró al jugador con apodo '" + apodoJugador + "'."))
                .getId();

        Long partidoId = partidoRepository.findFirstByRivalIgnoreCaseOrderByFechaPartidoDesc(nombreRival)
                .orElseThrow(() -> new RuntimeException("No se encontró el último partido contra '" + nombreRival + "'."))
                .getId();

        EstadisticasRequest request = new EstadisticasRequest(partidoId, jugadorId, goles, asistencias, 0, 0);
        estadisticasService.addOrUpdateStats(request);

        return "Estadísticas de " + apodoJugador + " añadidas al partido contra " + nombreRival + ".";
    }
}
