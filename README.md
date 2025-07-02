Fut7Stats AI ⚽🤖
Backend de una aplicación para gestionar estadísticas de equipos de fútbol 7, potenciada con una interfaz de IA que permite registrar datos mediante lenguaje natural.

Este proyecto está siendo desarrollado con la ayuda de un asistente de IA para explorar las capacidades de la integración de LLMs en una aplicación Java moderna.

✨ Características Principales
Gestión de Entidades: API REST para crear, leer y actualizar:

Equipos

Jugadores

Partidos

Estadísticas

Agente de IA Conversacional: Un único endpoint de API que recibe prompts en lenguaje natural para realizar acciones complejas, como:

Crear un jugador y añadirlo a un equipo.

Registrar un partido.

Añadir goles y asistencias a un jugador en un partido específico.

Mover un jugador de un equipo a otro.

Interfaz de Demostración: Una página web simple (HTML, CSS, JS) servida directamente por Spring Boot para interactuar con el agente de IA y visualizar los datos en tiempo real.

Base de Datos Persistente: Toda la información se almacena en una base de datos PostgreSQL.

🛠️ Stack Tecnológico
Backend:

Java 17

Spring Boot 3

Spring Data JPA (con Hibernate)

Base de Datos:

PostgreSQL (gestionada con Docker)

Inteligencia Artificial:

LangChain4j

OpenAI (GPT-4o / GPT-3.5-Turbo)

Frontend (Demo):

HTML5, CSS3, JavaScript (Vanilla JS)
