package com.fut7stats.backend.features.equipo.tool;

import com.fut7stats.backend.features.equipo.dto.CreateEquipoRequest;
import com.fut7stats.backend.features.equipo.dto.EquipoResponse;
import com.fut7stats.backend.features.equipo.dto.UpdateEquipoRequest;
import com.fut7stats.backend.features.equipo.service.EquipoService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EquipoTools {

    private final EquipoService equipoService;

    public EquipoTools(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @Tool("Crea un nuevo equipo de fútbol solo con el nombre especificado.")
    public String crearEquipo(
            @P("El nombre que se le dará al nuevo equipo.") String nombreEquipo
    ) {
        try {
            System.out.println("LOG: Herramienta 'crearEquipo' invocada para: " + nombreEquipo);
            CreateEquipoRequest request = new CreateEquipoRequest(nombreEquipo, null);
            EquipoResponse equipoCreado = equipoService.create(request);
            // Devolvemos un mensaje de éxito que la IA usará para responder.
            return "Equipo '" + equipoCreado.nombreEquipo() + "' creado con éxito.";
        } catch (RuntimeException e) {
            // Si el servicio lanza un error, lo capturamos y lo devolvemos como un string.
            System.err.println("ERROR en herramienta crearEquipo: " + e.getMessage());
            return "Error al crear el equipo: " + e.getMessage();
        }
    }

    @Tool("Crea un nuevo equipo de fútbol con un nombre y una URL para la imagen de su escudo.")
    public String crearEquipoConImagen(
            @P("El nombre que se le dará al nuevo equipo.") String nombreEquipo,
            @P("La URL completa de la imagen para el escudo del equipo.") String imagenUrl
    ) {
        try {
            System.out.println("LOG: Herramienta 'crearEquipoConImagen' invocada para: " + nombreEquipo);
            CreateEquipoRequest request = new CreateEquipoRequest(nombreEquipo, imagenUrl);
            EquipoResponse equipoCreado = equipoService.create(request);
            return "Equipo con escudo '" + equipoCreado.nombreEquipo() + "' creado con éxito.";
        } catch (RuntimeException e) {
            System.err.println("ERROR en herramienta crearEquipoConImagen: " + e.getMessage());
            return "Error al crear el equipo con imagen: " + e.getMessage();
        }
    }

    @Tool("Modifica los datos de un equipo existente, como su nombre o la URL de su escudo.")
    public String modificarEquipo(
            @P("El nombre actual del equipo que se va a modificar.") String nombreActual,
            @P("El nuevo nombre para el equipo. No incluir si no se cambia.") Optional<String> nuevoNombre,
            @P("La nueva URL de la imagen para el escudo. No incluir si no se cambia.") Optional<String> nuevaImagenUrl
    ) {
        try {
            System.out.println("LOG: Herramienta 'modificarEquipo' invocada para: " + nombreActual);
            Long equipoId = equipoService.findByNombre(nombreActual).id();
            UpdateEquipoRequest request = new UpdateEquipoRequest(nuevoNombre, nuevaImagenUrl);
            equipoService.update(equipoId, request);
            return "Equipo '" + nombreActual + "' actualizado correctamente.";
        } catch (RuntimeException e) {
            System.err.println("ERROR en la herramienta modificarEquipo: " + e.getMessage());
            return e.getMessage();
        }
    }
}