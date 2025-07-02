package com.aurasport.Aurasport.features.jugador.controller;

import com.aurasport.Aurasport.features.jugador.dto.CreateJugadorRequest;
import com.aurasport.Aurasport.features.jugador.dto.JugadorResponse;
import com.aurasport.Aurasport.features.jugador.service.JugadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<JugadorResponse> createJugador(@RequestBody CreateJugadorRequest createRequest) {
        JugadorResponse jugadorCreado = jugadorService.create(createRequest);
        return new ResponseEntity<>(jugadorCreado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JugadorResponse>> getJugadoresByEquipo(@RequestParam Long equipoId) {
        List<JugadorResponse> jugadores = jugadorService.findAllByEquipoId(equipoId);
        return ResponseEntity.ok(jugadores);
    }
}
