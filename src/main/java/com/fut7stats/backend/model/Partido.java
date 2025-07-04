package com.fut7stats.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "partidos")
@Getter
@Setter
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(nullable = false)
    private String rival;

    @Column(name = "fecha_partido", nullable = false)
    private OffsetDateTime fechaPartido;

    @Column
    private String lugar;

    @Column(name = "goles_favor")
    private int golesFavor;

    @Column(name = "goles_contra")
    private int golesContra;

    @Column(name = "fecha_creacion", updatable = false)
    private OffsetDateTime fechaCreacion = OffsetDateTime.now();
}