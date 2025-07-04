package com.fut7stats.backend.features.tituloequipo.repository;


import com.fut7stats.backend.model.TituloEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TituloEquipoRepository extends JpaRepository<TituloEquipo, Long> {

    List<TituloEquipo> findByEquipoIdOrderByFechaObtencionDesc(Long equipoId);
}