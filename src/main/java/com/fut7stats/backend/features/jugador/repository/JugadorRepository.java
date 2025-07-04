package com.fut7stats.backend.features.jugador.repository;


import com.fut7stats.backend.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    List<Jugador> findByEquipoId(Long equipoId);

    Optional<Jugador> findByEquipoIdAndApodoIgnoreCase(Long equipoId, String apodo);

    Optional<Jugador> findFirstByApodoIgnoreCase(String apodo);
}