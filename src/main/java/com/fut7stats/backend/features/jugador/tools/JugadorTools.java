package com.fut7stats.backend.features.jugador.tools;

import com.fut7stats.backend.features.equipo.repository.EquipoRepository;
import com.fut7stats.backend.features.jugador.dto.CreateJugadorRequest;
import com.fut7stats.backend.features.jugador.service.JugadorService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class JugadorTools {

    private final JugadorService jugadorService;
    private final EquipoRepository equipoRepository; // Lo necesitamos para buscar el equipo por nombre

    public JugadorTools(JugadorService jugadorService, EquipoRepository equipoRepository) {
        this.jugadorService = jugadorService;
        this.equipoRepository = equipoRepository;
    }

    @Tool("Crea un nuevo jugador y lo asigna a un equipo existente.")
    public String crearJugador(
            @P("Nombre completo del jugador.") String nombreCompleto,
            @P("Apodo o alias del jugador.") String apodo,
            @P("Posición en la que juega el jugador (delantero, defensa, etc.).") String posicion,
            @P("Nombre del equipo al que pertenecerá el jugador.") String nombreEquipo
    ) {
        System.out.println("LOG: Herramienta 'crearJugador' invocada.");

        // Lógica para encontrar el ID del equipo a partir de su nombre
        Long equipoId = equipoRepository.findByNombreEquipo(nombreEquipo)
                .orElseThrow(() -> new RuntimeException("No se encontró un equipo llamado '" + nombreEquipo + "'."))
                .getId();

        CreateJugadorRequest request = new CreateJugadorRequest(nombreCompleto, apodo, posicion, equipoId);
        var jugadorCreado = jugadorService.create(request);

        return "Jugador '" + jugadorCreado.nombreCompleto() + "' creado con éxito y añadido al equipo '" + nombreEquipo + "'.";
    }

    @Tool("Obtiene y lista todos los jugadores que pertenecen a un equipo específico.")
    public String listarJugadoresPorEquipo(
            @P("El nombre del equipo del cual se quieren listar los jugadores.") String nombreEquipo
    ) {
        System.out.println("LOG: Herramienta 'listarJugadoresPorEquipo' invocada para el equipo: " + nombreEquipo);

        // Buscamos el equipo para obtener su ID
        Long equipoId = equipoRepository.findByNombreEquipo(nombreEquipo)
                .orElseThrow(() -> new RuntimeException("No se encontró un equipo llamado '" + nombreEquipo + "'."))
                .getId();

        // Llamamos al servicio para obtener la lista de jugadores
        var jugadores = jugadorService.findAllByEquipoId(equipoId);

        if (jugadores.isEmpty()) {
            return "El equipo '" + nombreEquipo + "' no tiene ningún jugador registrado todavía.";
        }

        // Construimos una respuesta en texto formateado
        StringBuilder respuesta = new StringBuilder("Jugadores en el equipo '" + nombreEquipo + "':\n");
        jugadores.forEach(jugador -> {
            respuesta.append("- ")
                    .append(jugador.nombreCompleto())
                    .append(" (Apodo: ")
                    .append(jugador.apodo())
                    .append(", Posición: ")
                    .append(jugador.posicion())
                    .append(")\n");
        });

        return respuesta.toString();
    }
}
