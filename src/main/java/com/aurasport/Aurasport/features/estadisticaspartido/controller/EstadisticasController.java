package com.aurasport.Aurasport.features.estadisticaspartido.controller;

import com.aurasport.Aurasport.features.estadisticaspartido.dto.EstadisticasRequest;
import com.aurasport.Aurasport.features.estadisticaspartido.dto.EstadisticasResponse;
import com.aurasport.Aurasport.features.estadisticaspartido.service.EstadisticasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @PostMapping
    public ResponseEntity<EstadisticasResponse> addStats(@RequestBody EstadisticasRequest request) {
        EstadisticasResponse response = estadisticasService.addOrUpdateStats(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EstadisticasResponse>> getStatsByPartido(@RequestParam Long partidoId) {
        List<EstadisticasResponse> responses = estadisticasService.findByPartidoId(partidoId);
        return ResponseEntity.ok(responses);
    }
}
