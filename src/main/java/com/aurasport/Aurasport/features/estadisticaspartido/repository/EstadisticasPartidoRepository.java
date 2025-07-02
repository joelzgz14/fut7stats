package com.aurasport.Aurasport.features.estadisticaspartido.repository;


import com.aurasport.Aurasport.features.jugador.dto.JugadorStatsDTO;
import com.aurasport.Aurasport.model.EstadisticasPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstadisticasPartidoRepository extends JpaRepository<EstadisticasPartido, Long> {

    List<EstadisticasPartido> findByJugadorId(Long jugadorId);

    List<EstadisticasPartido> findByPartidoId(Long partidoId);

    Optional<EstadisticasPartido> findByPartidoIdAndJugadorId(Long partidoId, Long jugadorId);

    @Query("SELECT new com.aurasport.Aurasport.features.jugador.dto.JugadorStatsDTO(" + // <-- Faltaba el nombre de la clase aquí
            "SUM(e.goles), " +
            "SUM(e.asistencias)) " +
            "FROM EstadisticasPartido e " +
            "WHERE e.jugador.id = :jugadorId")
    JugadorStatsDTO findTotalStatsByJugadorId(@Param("jugadorId") Long jugadorId);

}