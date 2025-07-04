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

    // Inyectamos el EquipoService que ya tienes y funciona
    public EquipoTools(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @Tool("Crea un nuevo equipo de fútbol solo con el nombre especificado.")
    public EquipoResponse crearEquipo( // <-- CAMBIO: Devuelve el objeto de respuesta
                                       @P("El nombre que se le dará al nuevo equipo.") String nombreEquipo
    ) {
        System.out.println("LOG: Herramienta 'crearEquipo' (sin imagen) invocada con el nombre: " + nombreEquipo);
        CreateEquipoRequest request = new CreateEquipoRequest(nombreEquipo, null);

        // <-- CAMBIO: Devuelve directamente el resultado del servicio
        return equipoService.create(request);
    }

    @Tool("Crea un nuevo equipo de fútbol con un nombre y una URL para la imagen de su escudo.")
    public EquipoResponse crearEquipoConImagen( // <-- CAMBIO: Devuelve el objeto de respuesta
                                                @P("El nombre que se le dará al nuevo equipo.") String nombreEquipo,
                                                @P("La URL completa de la imagen para el escudo del equipo.") String imagenUrl
    ) {
        System.out.println("LOG: Herramienta 'crearEquipoConImagen' invocada con nombre: " + nombreEquipo);
        CreateEquipoRequest request = new CreateEquipoRequest(nombreEquipo, imagenUrl);

        // <-- CAMBIO: Devuelve directamente el resultado del servicio
        return equipoService.create(request);
    }

    @Tool("Modifica los datos de un equipo existente, como su nombre o la URL de su escudo.")
    public String modificarEquipo(
            @P("El nombre actual del equipo que se va a modificar.") String nombreActual,
            @P("El nuevo nombre para el equipo. No incluir si no se cambia.") Optional<String> nuevoNombre,
            @P("La nueva URL de la imagen para el escudo. No incluir si no se cambia.") Optional<String> nuevaImagenUrl
    ) {
        System.out.println("LOG: Herramienta 'modificarEquipo' invocada para: " + nombreActual);

        try {
            Long equipoId = equipoService.findByNombre(nombreActual).id();

            UpdateEquipoRequest request = new UpdateEquipoRequest(nuevoNombre, nuevaImagenUrl);
            EquipoResponse equipoActualizado = equipoService.update(equipoId, request);

            // Si todo va bien, devolvemos el objeto de respuesta
            // Langchain se lo pasará a la IA para que formule una respuesta final.
            return "Equipo '" + nombreActual + "' actualizado correctamente.";

        } catch (RuntimeException e) {
            // Si algo falla (ej: el equipo no se encuentra), capturamos el error
            // y devolvemos el mensaje de error como resultado de la herramienta.
            System.err.println("ERROR en la herramienta modificarEquipo: " + e.getMessage());
            return e.getMessage();
        }
    }
}