package com.fut7stats.backend.features.puntuacionpartido.repository;


import com.fut7stats.backend.model.PuntuacionesPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PuntuacionesPartidoRepository extends JpaRepository<PuntuacionesPartido, Long> {

    List<PuntuacionesPartido> findByPartidoId(Long partidoId);

    List<PuntuacionesPartido> findByPartidoIdAndEvaluadoId(Long partidoId, Long evaluadoId);
}
