package com.fut7stats.backend.features.partido.repository;


import com.fut7stats.backend.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByEquipoIdOrderByFechaPartidoDesc(Long equipoId);

    // Busca el primer partido que coincida con el nombre del rival (ignorando mayúsculas/minúsculas)
    // y lo ordena por fecha descendente para obtener el más reciente.
    Optional<Partido> findFirstByRivalIgnoreCaseOrderByFechaPartidoDesc(String rival);
}