package com.aurasport.Aurasport.features.jugador.service;


import com.aurasport.Aurasport.features.equipo.repository.EquipoRepository;
import com.aurasport.Aurasport.features.estadisticaspartido.repository.EstadisticasPartidoRepository;
import com.aurasport.Aurasport.features.jugador.dto.CreateJugadorRequest;
import com.aurasport.Aurasport.features.jugador.dto.JugadorResponse;
import com.aurasport.Aurasport.features.jugador.dto.JugadorStatsDTO;
import com.aurasport.Aurasport.features.jugador.dto.UpdateJugadorRequest;
import com.aurasport.Aurasport.features.jugador.mapper.JugadorMapper;
import com.aurasport.Aurasport.features.jugador.repository.JugadorRepository;
import com.aurasport.Aurasport.model.Equipo;
import com.aurasport.Aurasport.model.Jugador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;
    private final EstadisticasPartidoRepository estadisticasRepository;
    private final JugadorMapper jugadorMapper;

    public JugadorService(JugadorRepository jugadorRepository,
                          EquipoRepository equipoRepository,
                          JugadorMapper jugadorMapper,
                          EstadisticasPartidoRepository estadisticasRepository) {
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
        this.jugadorMapper = jugadorMapper;
        this.estadisticasRepository = estadisticasRepository;
    }

    @Transactional
    public JugadorResponse create(CreateJugadorRequest request) {
        Equipo equipo = equipoRepository.findById(request.equipoId())
                .orElseThrow(() -> new RuntimeException("El equipo con id " + request.equipoId() + " no existe."));

        if (request.apodo() != null && !request.apodo().isBlank()) {
            jugadorRepository.findByEquipoIdAndApodoIgnoreCase(request.equipoId(), request.apodo())
                    .ifPresent(jugadorExistente -> {
                        throw new RuntimeException("El apodo '" + request.apodo() + "' ya está en uso en este equipo.");
                    });
        }

        Jugador jugador = new Jugador();
        jugador.setNombreCompleto(request.nombreCompleto());
        jugador.setApodo(request.apodo());
        jugador.setPosicion(request.posicion());
        jugador.setEquipo(equipo);

        Jugador jugadorGuardado = jugadorRepository.save(jugador);

        // --- CORRECCIÓN ---
        // Un jugador nuevo siempre tiene 0 estadísticas.
        return jugadorMapper.toResponse(jugadorGuardado, new JugadorStatsDTO(0L, 0L));
    }

    @Transactional(readOnly = true)
    public List<JugadorResponse> findAllByEquipoId(Long equipoId) {
        if (!equipoRepository.existsById(equipoId)) {
            throw new RuntimeException("El equipo con id " + equipoId + " no existe.");
        }

        // --- CORRECCIÓN ---
        // Obtenemos los jugadores y luego, para cada uno, sus estadísticas totales.
        List<Jugador> jugadores = jugadorRepository.findByEquipoId(equipoId);

        return jugadores.stream().map(jugador -> {
            JugadorStatsDTO stats = estadisticasRepository.findTotalStatsByJugadorId(jugador.getId());
            if (stats == null) {
                stats = new JugadorStatsDTO(0L, 0L);
            }
            return jugadorMapper.toResponse(jugador, stats);
        }).collect(Collectors.toList());
    }

    // --- MÉTODO NUEVO ---
    @Transactional
    public JugadorResponse update(Long jugadorId, UpdateJugadorRequest request) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + jugadorId));

        request.nuevoNombreCompleto().ifPresent(jugador::setNombreCompleto);
        request.nuevoApodo().ifPresent(jugador::setApodo);
        request.nuevaPosicion().ifPresent(jugador::setPosicion);

        Jugador jugadorActualizado = jugadorRepository.save(jugador);

        JugadorStatsDTO stats = estadisticasRepository.findTotalStatsByJugadorId(jugadorId);
        return jugadorMapper.toResponse(jugadorActualizado, stats);
    }

    // --- MÉTODO NUEVO ---
    @Transactional
    public JugadorResponse cambiarDeEquipo(Long jugadorId, Long nuevoEquipoId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + jugadorId));

        Equipo nuevoEquipo = equipoRepository.findById(nuevoEquipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + nuevoEquipoId));

        jugador.setEquipo(nuevoEquipo);
        Jugador jugadorActualizado = jugadorRepository.save(jugador);

        JugadorStatsDTO stats = estadisticasRepository.findTotalStatsByJugadorId(jugadorId);
        return jugadorMapper.toResponse(jugadorActualizado, stats);
    }
}