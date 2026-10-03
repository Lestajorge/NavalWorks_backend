· NavalWorks 🚢⚙️
Sistema Backend de Gestión de Trabajos y Trazabilidad Naval (Proyecto Fragatas F-110)
NavalWorks es una API REST empresarial desarrollada con Spring Boot 3, diseñada para digitalizar la trazabilidad y asignación de trabajos en astilleros navales. 
Basado en el flujo de montaje de las Fragatas F-110 (Navantia), el sistema gestiona bloques estructurales, planos isométricos, órdenes de trabajo y personal técnico.

· Arquitectura "API-First" y Multiplataforma
El proyecto sigue una estrategia Backend-First, exponiendo una API JSON estándar y desacoplada que permite conectar múltiples tipos de clientes según las necesidades del astillero:
Separación por Capas (N-Tier): Organización estricta en Controller, Service, Repository, Model y DTO.
Patrón DTO: Encapsulamiento del dominio mediante objetos de transferencia de datos (BloqueDTO, IsometricoDTO, TrabajoDTO, OperarioDTO) para garantizar la seguridad de los datos y evitar acoplamiento con la interfaz gráfica.
Preparado para Clientes Ligeros: Diseñado para responder a peticiones de terminales de escritorio en oficinas técnicas o tablets industriales a pie de obra.

· Stack Tecnológico
Lenguaje: Java 17+
Framework Principal: Spring Boot 3.x
Persistencia: Spring Data JPA / Hibernate
Base de Datos: MySQL
Seguridad: Spring Security 6.x
Gestión de Dependencias: Maven
Cliente Objetivo (En evaluación): Multiplataforma (JavaFX / Flutter)

· Modelo de Dominio
Jerarquía del dominio:
Bloque (ID de Bloque) -> Isométrico (Plano) -> Trabajo (Montaje/Soldadura) -> Operario (Personal)

·Entidades Principales y Roles
Bloque: Bloque estructural físico de la fragata (ej. B322, B631).
Isométrico: Planos de tuberías asignados a bloques específicos.
Trabajo: Órdenes individuales de soldadura o montaje con seguimiento de estado (PENDIENTE, EN_MONTAJE, MONTADO, SOLDADO , REVISADO).
Operario: Personal técnico del astillero (TUBERO, SOLDADOR, INSPECTOR_CALIDAD).

·Endpoints
Actualmente, la capa de controladores y exposición de servicios REST se encuentra en proceso de implementación y ajuste. 
La estructura planificada para la API sigue el estándar RESTful con las siguientes rutas principales:
Módulo de Bloques (/api/bloques): Consulta de bloques estructurales y agregación de estados de montaje.
Módulo de Isométricos (/api/isometricos): Gestión de planos de tubería asociados a cada bloque.
Módulo de Trabajos (/api/trabajos): Creación, asignación y cambio de estado de órdenes de trabajo (soldadura/montaje).
Módulo de Operarios (/api/operarios): Gestión de personal y roles técnicos del astillero.
Autenticación (/api/auth / /login): Gestión de credenciales y control de acceso por roles con Spring Security.


· Ejecución Local
Requisitos Previos
JDK 17 o superior
Maven 3.8+
MySQL 8.0+
Instalación y Despliegue
Clonar el repositorio:
git clone https://github.com/Lestajorge/NavalWorks.git
cd NavalWorks
Configurar la Conexión a la Base de Datos en src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/navalworks_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
Compilar y Ejecutar:
mvn clean install
mvn spring-boot:run

La API REST se iniciará en http://localhost:8080.

· Contexto del Proyecto (Grado Superior DAM)
Este repositorio contiene la arquitectura backend del Proyecto Integrado de Fin de Grado en Desarrollo de Aplicaciones Multiplataforma (DAM). 
Demuestra la capacidad de diseñar e implementar un servicio de grado industrial orientado al sector naval aplicando las mejores prácticas de desarrollo backend en Java.

· Autor
Jorge Lesta - Desarrollador Backend & Estudiante DAM
GitHub: @Lestajorge
