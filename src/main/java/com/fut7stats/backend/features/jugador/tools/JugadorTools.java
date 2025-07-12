package com.fut7stats.backend.features.jugador.tools;

import com.fut7stats.backend.features.equipo.repository.EquipoRepository;
import com.fut7stats.backend.features.jugador.dto.CreateJugadorRequest;
import com.fut7stats.backend.features.jugador.dto.UpdateJugadorRequest;
import com.fut7stats.backend.features.jugador.repository.JugadorRepository;
import com.fut7stats.backend.features.jugador.service.JugadorService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JugadorTools {

    private final JugadorService jugadorService;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    public JugadorTools(JugadorService jugadorService, EquipoRepository equipoRepository, JugadorRepository jugadorRepository) {
        this.jugadorService = jugadorService;
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Tool("Crea un nuevo jugador. Si falta información clave como el nombre del equipo, el nombre completo o el apodo, la solicitará.")
    public String crearJugador(
            @P("Nombre del equipo existente al que pertenecerá el jugador.") Optional<String> nombreEquipo,
            @P("Nombre completo y apellidos del jugador.") Optional<String> nombreCompleto,
            @P("Apodo o alias único para el jugador.") Optional<String> apodo,
            @P("Posición en la que juega (delantero, defensa, etc). Es opcional.") Optional<String> posicion
    ) {
        // --- VALIDACIÓN ---
        if (nombreEquipo.isEmpty() || nombreCompleto.isEmpty() || apodo.isEmpty()) {
            return "¡Entendido! Para crear un jugador necesito que me des su nombre completo, su apodo y el nombre del equipo al que pertenece.";
        }

        try {
            Long equipoId = equipoRepository.findByNombreEquipo(nombreEquipo.get())
                    .orElseThrow(() -> new RuntimeException("No se encontró un equipo llamado '" + nombreEquipo.get() + "'."))
                    .getId();

            CreateJugadorRequest request = new CreateJugadorRequest(nombreCompleto.get(), apodo.get(), posicion.orElse(null), equipoId);
            var jugadorCreado = jugadorService.create(request);

            return "Jugador '" + jugadorCreado.nombreCompleto() + "' creado con éxito en el equipo '" + nombreEquipo.get() + "'.";
        } catch (Exception e) {
            return "Error al crear jugador: " + e.getMessage();
        }
    }

    @Tool("Mueve o cambia un jugador de su equipo actual a un nuevo equipo.")
    public String cambiarJugadorDeEquipo(
            @P("Apodo del jugador que será movido.") String apodoJugador,
            @P("Nombre del nuevo equipo al que se unirá el jugador.") String nombreNuevoEquipo
    ) {
        try {
            System.out.println("LOG: Herramienta 'cambiarJugadorDeEquipo' invocada.");
            Long jugadorId = jugadorRepository.findFirstByApodoIgnoreCase(apodoJugador)
                    .orElseThrow(() -> new RuntimeException("No se encontró un jugador con el apodo '" + apodoJugador + "'."))
                    .getId();
            Long nuevoEquipoId = equipoRepository.findByNombreEquipo(nombreNuevoEquipo)
                    .orElseThrow(() -> new RuntimeException("No se encontró un equipo llamado '" + nombreNuevoEquipo + "'."))
                    .getId();
            jugadorService.cambiarDeEquipo(jugadorId, nuevoEquipoId);
            return "¡Fichaje completado! El jugador " + apodoJugador + " ahora forma parte del equipo " + nombreNuevoEquipo + ".";
        } catch (Exception e) {
            System.err.println("ERROR en herramienta cambiarJugadorDeEquipo: " + e.getMessage());
            return "Error al cambiar de equipo: " + e.getMessage();
        }
    }

    @Tool("Modifica los datos de un jugador existente, como su nombre, apodo o posición.")
    public String modificarJugador(
            @P("Apodo actual del jugador que se quiere modificar.") String apodoActual,
            @P("El nuevo nombre completo del jugador. No incluir si no se cambia.") Optional<String> nuevoNombre,
            @P("El nuevo apodo del jugador. No incluir si no se cambia.") Optional<String> nuevoApodo,
            @P("La nueva posición del jugador. No incluir si no se cambia.") Optional<String> nuevaPosicion
    ) {
        try {
            System.out.println("LOG: Herramienta 'modificarJugador' invocada para: " + apodoActual);
            Long jugadorId = jugadorRepository.findFirstByApodoIgnoreCase(apodoActual)
                    .orElseThrow(() -> new RuntimeException("No se encontró un jugador con apodo '" + apodoActual + "'."))
                    .getId();
            UpdateJugadorRequest request = new UpdateJugadorRequest(nuevoNombre, nuevoApodo, nuevaPosicion);
            jugadorService.update(jugadorId, request);
            return "Datos del jugador " + apodoActual + " actualizados correctamente.";
        } catch (Exception e) {
            System.err.println("ERROR en la herramienta modificarJugador: " + e.getMessage());
            return "Error al modificar jugador: " + e.getMessage();
        }
    }
}