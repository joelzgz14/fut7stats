package com.aurasport.Aurasport.features.partido.service;


import com.aurasport.Aurasport.features.equipo.repository.EquipoRepository;
import com.aurasport.Aurasport.features.partido.dto.CreatePartidoRequest;
import com.aurasport.Aurasport.features.partido.dto.PartidoResponse;
import com.aurasport.Aurasport.features.partido.dto.UpdateResultadoRequest;
import com.aurasport.Aurasport.features.partido.mapper.PartidoMapper;
import com.aurasport.Aurasport.features.partido.repository.PartidoRepository;
import com.aurasport.Aurasport.model.Equipo;
import com.aurasport.Aurasport.model.Partido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final PartidoMapper partidoMapper;

    public PartidoService(PartidoRepository partidoRepository,
                          EquipoRepository equipoRepository,
                          PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.equipoRepository = equipoRepository;
        this.partidoMapper = partidoMapper;
    }

    @Transactional
    public PartidoResponse create(CreatePartidoRequest request) {
        // Lógica de negocio: Validar que el equipo existe antes de crear el partido.
        Equipo equipo = equipoRepository.findById(request.equipoId())
                .orElseThrow(() -> new RuntimeException("El equipo con id " + request.equipoId() + " no existe."));

        Partido partido = new Partido();
        partido.setRival(request.rival());
        partido.setFechaPartido(request.fechaPartido());
        partido.setLugar(request.lugar());
        partido.setEquipo(equipo);

        Partido partidoGuardado = partidoRepository.save(partido);
        return partidoMapper.toResponse(partidoGuardado);
    }

    @Transactional(readOnly = true)
    public List<PartidoResponse> findAllByEquipoId(Long equipoId) {
        if (!equipoRepository.existsById(equipoId)) {
            throw new RuntimeException("El equipo con id " + equipoId + " no existe.");
        }
        return partidoRepository.findByEquipoIdOrderByFechaPartidoDesc(equipoId)
                .stream()
                .map(partidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PartidoResponse actualizarResultado(Long partidoId, UpdateResultadoRequest request) {
        // 1. Buscamos el partido existente
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + partidoId));

        // 2. Actualizamos los campos del resultado
        partido.setGolesFavor(request.golesFavor());
        partido.setGolesContra(request.golesContra());

        // 3. Guardamos los cambios
        Partido partidoActualizado = partidoRepository.save(partido);

        // 4. Devolvemos la respuesta actualizada
        return partidoMapper.toResponse(partidoActualizado);
    }
}
