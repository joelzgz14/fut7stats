package com.fut7stats.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "puntuaciones_partido")
@Getter
@Setter
public class PuntuacionesPartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluador_jugador_id", nullable = false)
    private Jugador evaluador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluado_jugador_id", nullable = false)
    private Jugador evaluado;

    @Column(nullable = false)
    private int puntuacion;
}
