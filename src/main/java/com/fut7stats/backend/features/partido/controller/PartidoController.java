package com.fut7stats.backend.features.partido.controller;

import com.fut7stats.backend.features.partido.dto.CreatePartidoRequest;
import com.fut7stats.backend.features.partido.dto.PartidoResponse;
import com.fut7stats.backend.features.partido.dto.UpdateResultadoRequest;
import com.fut7stats.backend.features.partido.service.PartidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @PostMapping
    public ResponseEntity<PartidoResponse> createPartido(@RequestBody CreatePartidoRequest createRequest) {
        PartidoResponse partidoCreado = partidoService.create(createRequest);
        return new ResponseEntity<>(partidoCreado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PartidoResponse>> getPartidosByEquipo(@RequestParam Long equipoId) {
        List<PartidoResponse> partidos = partidoService.findAllByEquipoId(equipoId);
        return ResponseEntity.ok(partidos);
    }

    // Usamos PUT para actualizar el resultado de un partido específico
    @PutMapping("/{partidoId}/resultado")
    public ResponseEntity<PartidoResponse> updateResultado(
            @PathVariable Long partidoId,
            @RequestBody UpdateResultadoRequest request) {

        PartidoResponse partidoActualizado = partidoService.actualizarResultado(partidoId, request);
        return ResponseEntity.ok(partidoActualizado);
    }
}
