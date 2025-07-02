package com.aurasport.Aurasport.features.equipo.tool;

import com.aurasport.Aurasport.features.equipo.dto.CreateEquipoRequest;
import com.aurasport.Aurasport.features.equipo.dto.EquipoResponse;
import com.aurasport.Aurasport.features.equipo.service.EquipoService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class EquipoTools {

    private final EquipoService equipoService;

    // Inyectamos el EquipoService que ya tienes y funciona
    public EquipoTools(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Esta es la herramienta que la IA podrá usar.
    // La descripción es MUY importante para que la IA sepa cuándo usarla.
    @Tool("Crea un nuevo equipo de fútbol con el nombre que se especifica.")
    public String crearEquipo(
            @P("El nombre que se le dará al nuevo equipo.") String nombreEquipo
    ){
        System.out.println("LOG: La herramienta de IA 'crearEquipo' ha sido invocada con el nombre: " + nombreEquipo);

        // 1. Preparamos la petición para tu servicio existente
        CreateEquipoRequest request = new CreateEquipoRequest(nombreEquipo);

        // 2. Llamamos a tu EquipoService, que ya tiene toda la lógica
        EquipoResponse equipoCreado = equipoService.create(request);

        // 3. Devolvemos una respuesta en texto para el usuario final
        return "¡Equipo creado con éxito! El nuevo equipo es '" + equipoCreado.nombreEquipo() + "' con ID: " + equipoCreado.id();
    }
}