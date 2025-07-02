package com.aurasport.Aurasport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "titulos_equipo")
@Getter
@Setter
public class TituloEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(name = "nombre_titulo", nullable = false)
    private String nombreTitulo;

    @Column
    private String temporada;

    @Column(name = "fecha_obtencion", nullable = false)
    private LocalDate fechaObtencion;

    @Column(name = "fecha_creacion", updatable = false)
    private OffsetDateTime fechaCreacion = OffsetDateTime.now();
}