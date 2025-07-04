package com.fut7stats.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relaciones ---
    // Muchos jugadores pueden pertenecer a Un equipo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    // --- Columnas ---
    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "apodo")
    private String apodo;

    @Column(name = "posicion")
    private String posicion;

    @Column(name = "activo")
    private boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private OffsetDateTime fechaCreacion = OffsetDateTime.now();
}