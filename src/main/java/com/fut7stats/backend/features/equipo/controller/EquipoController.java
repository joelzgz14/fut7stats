package com.fut7stats.backend.features.equipo.controller;

import com.fut7stats.backend.features.equipo.dto.CreateEquipoRequest;
import com.fut7stats.backend.features.equipo.dto.EquipoResponse;
import com.fut7stats.backend.features.equipo.service.EquipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Anotación que combina @Controller y @ResponseBody. Convierte respuestas a JSON.
@RequestMapping("/api/v1/equipos") // Define la URL base para todos los métodos de esta clase.
public class EquipoController {

    private final EquipoService equipoService;

    // Inyectamos el servicio por constructor, como buena práctica.
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // --- Endpoints ---

    @PostMapping
    public ResponseEntity<EquipoResponse> createEquipo(@RequestBody CreateEquipoRequest createRequest) {
        EquipoResponse equipoCreado = equipoService.create(createRequest);
        // Devolvemos un código 201 Created, que es el estándar para creación de recursos.
        return new ResponseEntity<>(equipoCreado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EquipoResponse>> getAllEquipos() {
        List<EquipoResponse> equipos = equipoService.findAll();
        // Devolvemos un código 200 OK, estándar para peticiones GET exitosas.
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> getEquipoById(@PathVariable Long id) {
        EquipoResponse equipo = equipoService.findById(id);
        return new ResponseEntity<>(equipo, HttpStatus.OK);
    }
}