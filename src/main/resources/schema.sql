--CREATE DATABASE fut7_db;

-- 1. Tabla de Equipos (Nuestros "inquilinos")
CREATE TABLE equipos (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre_equipo VARCHAR(100) NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabla de Jugadores (pertenecen a un equipo)
CREATE TABLE jugadores (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    equipo_id BIGINT NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    apodo VARCHAR(50),
    posicion VARCHAR(50),
    activo BOOLEAN DEFAULT true,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (equipo_id) REFERENCES equipos(id),
    UNIQUE (equipo_id, apodo)
);

-- 3. Tabla de Partidos (jugados por un equipo)
CREATE TABLE partidos (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    equipo_id BIGINT NOT NULL,
    rival VARCHAR(100) NOT NULL,
    fecha_partido TIMESTAMP WITH TIME ZONE NOT NULL,
    lugar VARCHAR(255),
    goles_favor INT DEFAULT 0,
    goles_contra INT DEFAULT 0,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (equipo_id) REFERENCES equipos(id)
);

-- 4. Tabla de Estadísticas (eventos de un jugador en un partido)
CREATE TABLE estadisticas_partido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    equipo_id BIGINT NOT NULL,
    partido_id BIGINT NOT NULL,
    jugador_id BIGINT NOT NULL,
    goles INT DEFAULT 0 NOT NULL,
    asistencias INT DEFAULT 0 NOT NULL,
    tarjetas_amarillas INT DEFAULT 0 NOT NULL,
    tarjetas_rojas INT DEFAULT 0 NOT NULL,

    FOREIGN KEY (equipo_id) REFERENCES equipos(id),
    FOREIGN KEY (partido_id) REFERENCES partidos(id),
    FOREIGN KEY (jugador_id) REFERENCES jugadores(id),
    UNIQUE (partido_id, jugador_id)
);

-- 5. Tabla de Puntuaciones (un jugador puntúa a otro en un partido)
CREATE TABLE puntuaciones_partido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    equipo_id BIGINT NOT NULL,
    partido_id BIGINT NOT NULL,
    evaluador_jugador_id BIGINT NOT NULL,
    evaluado_jugador_id BIGINT NOT NULL,
    puntuacion INT NOT NULL,

    FOREIGN KEY (equipo_id) REFERENCES equipos(id),
    FOREIGN KEY (partido_id) REFERENCES partidos(id),
    FOREIGN KEY (evaluador_jugador_id) REFERENCES jugadores(id),
    FOREIGN KEY (evaluado_jugador_id) REFERENCES jugadores(id),
    UNIQUE (partido_id, evaluador_jugador_id, evaluado_jugador_id),
    CHECK (evaluador_jugador_id <> evaluado_jugador_id)
);

-- 6. Tabla de Títulos (logros de un equipo)
CREATE TABLE titulos_equipo (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    equipo_id BIGINT NOT NULL,
    nombre_titulo VARCHAR(255) NOT NULL,
    temporada VARCHAR(50),
    fecha_obtencion DATE NOT NULL,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (equipo_id) REFERENCES equipos(id)
);

-- MENSAJE DE CONFIRMACIÓN AL FINAL
SELECT 'Script ejecutado correctamente. Se han creado 6 tablas.';