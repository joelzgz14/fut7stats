package com.fut7stats.backend.features.equipo.repository;

import com.fut7stats.backend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByNombreEquipo(String nombreEquipo);
}
