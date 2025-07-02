package com.aurasport.Aurasport.features.puntuacionpartido.repository;


import com.aurasport.Aurasport.model.PuntuacionesPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PuntuacionesPartidoRepository extends JpaRepository<PuntuacionesPartido, Long> {

    List<PuntuacionesPartido> findByPartidoId(Long partidoId);

    List<PuntuacionesPartido> findByPartidoIdAndEvaluadoId(Long partidoId, Long evaluadoId);
}
