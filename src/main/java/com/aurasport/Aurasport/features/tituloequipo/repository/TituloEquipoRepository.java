package com.aurasport.Aurasport.features.tituloequipo.repository;


import com.aurasport.Aurasport.model.TituloEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TituloEquipoRepository extends JpaRepository<TituloEquipo, Long> {

    List<TituloEquipo> findByEquipoIdOrderByFechaObtencionDesc(Long equipoId);
}