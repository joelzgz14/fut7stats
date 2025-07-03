package com.aurasport.Aurasport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "equipos")
@Getter
@Setter
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_equipo", nullable = false, unique = true)
    private String nombreEquipo;

    @Column(name = "fecha_creacion", updatable = false)
    private OffsetDateTime fechaCreacion = OffsetDateTime.now();

    @Column(name = "imagen_url") // NUEVO CAMPO
    private String imagenUrl;
}