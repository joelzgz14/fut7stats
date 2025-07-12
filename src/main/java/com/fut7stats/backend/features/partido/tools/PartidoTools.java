package com.fut7stats.backend.features.partido.tools;

import com.fut7stats.backend.features.equipo.repository.EquipoRepository;
import com.fut7stats.backend.features.partido.dto.CreatePartidoRequest;
import com.fut7stats.backend.features.partido.dto.UpdateResultadoRequest;
import com.fut7stats.backend.features.partido.repository.PartidoRepository;
import com.fut7stats.backend.features.partido.service.PartidoService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    @Tool("Crea un nuevo partido. Si falta el nombre del equipo o el rival, lo solicitará.")
    public String crearPartido(
            @P("Nombre del equipo local.") Optional<String> nombreEquipo,
            @P("Nombre del equipo rival.") Optional<String> nombreRival,
            @P("Fecha y hora del partido en formato ISO (ej: 2025-07-03T20:00:00Z). Si no se proporciona, se usará la fecha actual.") Optional<String> fecha,
            @P("Lugar donde se jugará el partido. Es opcional.") Optional<String> lugar
    ) {
        // --- VALIDACIÓN ---
        if (nombreEquipo.isEmpty() || nombreRival.isEmpty()) {
            return "¡De acuerdo! Para registrar un partido, necesito saber qué equipo juega y contra qué rival.";
        }

        try {
            Long equipoId = equipoRepository.findByNombreEquipo(nombreEquipo.get())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado: " + nombreEquipo.get()))
                    .getId();

            // Si no se proporciona fecha, usamos la actual.
            OffsetDateTime fechaPartido = fecha.map(OffsetDateTime::parse).orElse(OffsetDateTime.now());

            CreatePartidoRequest request = new CreatePartidoRequest(nombreRival.get(), fechaPartido, lugar.orElse(null), equipoId);
            var partidoCreado = partidoService.create(request);

            return "Partido registrado con éxito: " + nombreEquipo.get() + " vs " + nombreRival.get() + ".";
        } catch (Exception e) {
            return "Error al crear partido: " + e.getMessage();
        }
    }
}