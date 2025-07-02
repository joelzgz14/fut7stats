package com.aurasport.Aurasport.features.estadisticaspartido.service;

import com.aurasport.Aurasport.features.estadisticaspartido.dto.EstadisticasRequest;
import com.aurasport.Aurasport.features.estadisticaspartido.dto.EstadisticasResponse;
import com.aurasport.Aurasport.features.estadisticaspartido.mapper.EstadisticasMapper;
import com.aurasport.Aurasport.features.estadisticaspartido.repository.EstadisticasPartidoRepository;
import com.aurasport.Aurasport.features.jugador.repository.JugadorRepository;
import com.aurasport.Aurasport.features.partido.repository.PartidoRepository;
import com.aurasport.Aurasport.model.EstadisticasPartido;
import com.aurasport.Aurasport.model.Jugador;
import com.aurasport.Aurasport.model.Partido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {

    private final EstadisticasPartidoRepository estadisticasRepository;
    private final PartidoRepository partidoRepository;
    private final JugadorRepository jugadorRepository;
    private final EstadisticasMapper estadisticasMapper;

    public EstadisticasService(EstadisticasPartidoRepository eR, PartidoRepository pR, JugadorRepository jR, EstadisticasMapper eM) {
        this.estadisticasRepository = eR;
        this.partidoRepository = pR;
        this.jugadorRepository = jR;
        this.estadisticasMapper = eM;
    }

    @Transactional
    public EstadisticasResponse addOrUpdateStats(EstadisticasRequest request) {
        Partido partido = partidoRepository.findById(request.partidoId()).orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        Jugador jugador = jugadorRepository.findById(request.jugadorId()).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        EstadisticasPartido estadisticas = estadisticasRepository.findByPartidoIdAndJugadorId(request.partidoId(), request.jugadorId()).orElse(new EstadisticasPartido());

        if (estadisticas.getId() == null) {
            estadisticas.setPartido(partido);
            estadisticas.setJugador(jugador);
            estadisticas.setEquipo(partido.getEquipo());
        }

        estadisticas.setGoles(estadisticas.getGoles() + request.goles());
        estadisticas.setAsistencias(estadisticas.getAsistencias() + request.asistencias());

        EstadisticasPartido saved = estadisticasRepository.save(estadisticas);
        return estadisticasMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EstadisticasResponse> findByPartidoId(Long partidoId) {
        return estadisticasRepository.findByPartidoId(partidoId)
                .stream()
                .map(estadisticasMapper::toResponse)
                .collect(Collectors.toList());
    }
}