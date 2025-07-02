package com.aurasport.Aurasport.features.equipo.repository;

import com.aurasport.Aurasport.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByNombreEquipo(String nombreEquipo);
}
