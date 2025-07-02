package com.aurasport.Aurasport.features.equipo.service;

import com.aurasport.Aurasport.features.equipo.dto.CreateEquipoRequest;
import com.aurasport.Aurasport.features.equipo.dto.EquipoResponse;
import com.aurasport.Aurasport.features.equipo.mapper.EquipoMapper;
import com.aurasport.Aurasport.features.equipo.repository.EquipoRepository;
import com.aurasport.Aurasport.model.Equipo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // Anotación clave que define esta clase como un Service
public class EquipoService {

    // --- Inyección de Dependencias por Constructor (la mejor práctica) ---
    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;

    // Spring verá que este constructor necesita un EquipoRepository y un EquipoMapper,
    // y como los tenemos definidos como @Repository y @Component, nos los pasará automáticamente.
    public EquipoService(EquipoRepository equipoRepository, EquipoMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
    }

    // --- Métodos con la Lógica de Negocio ---

    @Transactional
    public EquipoResponse create(CreateEquipoRequest createRequest) {
        // Aquí no hay lógica de negocio compleja, simplemente creamos el equipo.
        // En servicios más complejos, aquí validaríamos datos, etc.
        Equipo equipo = equipoMapper.toEntity(createRequest);
        Equipo equipoGuardado = equipoRepository.save(equipo);
        return equipoMapper.toResponse(equipoGuardado);
    }

    @Transactional(readOnly = true) // readOnly = true es una optimización para consultas
    public List<EquipoResponse> findAll() {
        return equipoRepository.findAll()
                .stream()
                .map(equipoMapper::toResponse) // Equivalente a .map(equipo -> equipoMapper.toResponse(equipo))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EquipoResponse findById(Long id) {
        // findById devuelve un Optional, debemos manejar el caso de que no lo encuentre.
        return equipoRepository.findById(id)
                .map(equipoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id)); // Más adelante crearemos excepciones personalizadas
    }
}