package com.aurasport.Aurasport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estadisticas_partido")
@Getter
@Setter
public class EstadisticasPartido {

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
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @Column(nullable = false)
    private int goles = 0;

    @Column(nullable = false)
    private int asistencias = 0;

    @Column(name = "tarjetas_amarillas", nullable = false)
    private int tarjetasAmarillas = 0;

    @Column(name = "tarjetas_rojas", nullable = false)
    private int tarjetasRojas = 0;
}