· NavalWorks 🚢⚙️
Sistema Backend de Gestión de Trabajos y Trazabilidad Naval (Proyecto Fragatas F-110)
NavalWorks es un backend API REST desarrollado con Spring Boot para digitalizar la trazabilidad y asignación de trabajos en astilleros navales. No renderiza pantallas: las aplicaciones nativas de Android o escritorio consumen la API y se encargan de la interfaz.
Basado en el flujo de montaje de las Fragatas F-110 (Navantia), el sistema gestiona bloques estructurales, planos isométricos, órdenes de trabajo y personal técnico.

· Arquitectura "API-First" y Multiplataforma
El proyecto sigue una estrategia Backend-First y expone una API JSON desacoplada:
Separación por capas: organización en Controller, Service, Repository, Model y DTO.
Patrón DTO: transferencia de datos mediante BloqueDTO, IsometricoDTO, TrabajoDTO y OperarioDTO, sin exponer directamente las entidades.
Clientes: preparado para que aplicaciones nativas de Android o escritorio consuman los endpoints REST.

· Stack Tecnológico
Lenguaje: Java 23
Framework Principal: Spring Boot 4.1.1
Persistencia: Spring Data JPA / Hibernate
Base de Datos: MySQL
Seguridad: Spring Security con HTTP Basic sin estado
Gestión de Dependencias: Maven
Interfaz de usuario: responsabilidad exclusiva de las aplicaciones cliente; el backend no utiliza Thymeleaf.

· Modelo de Dominio
Jerarquía del dominio:
Bloque (ID de Bloque) -> Isométrico (Plano) -> Trabajo (Montaje/Soldadura) -> Operario (Personal)

·Entidades Principales y Roles
Bloque: Bloque estructural físico de la fragata (ej. B322, B631).
Isométrico: Planos de tuberías asignados a bloques específicos.
Trabajo: Órdenes individuales de soldadura o montaje con seguimiento de estado (PENDIENTE, EN_MONTAJE, MONTADO, SOLDADO , REVISADO).
Operario: Personal técnico del astillero (TUBERO, SOLDADOR, INSPECTOR_CALIDAD).

·Endpoints
La API REST dispone de operaciones de consulta y gestión para los siguientes recursos:
Módulo de Bloques (/api/bloques): CRUD y búsqueda por código.
Módulo de Isométricos (/api/isometricos): CRUD, búsqueda y gestión de PDFs de planos de tubería.
Módulo de Trabajos (/api/trabajos): CRUD y consulta por isométrico, estado u operario.
Módulo de Operarios (/api/operarios): CRUD y búsqueda por especialista, nombre o apellidos.
Autenticación: el inicio de sesión normal valida las credenciales del operario. Para activar el modo administrador, el cliente solicita de nuevo las credenciales y usa HTTP Basic con esa cuenta. Las operaciones de escritura requieren el rol de administrador; la creación de operarios (POST /api/operarios) sigue permitida sin autenticación.

La cuenta inicial se crea al arrancar si se configura `APP_ADMIN_PASSWORD` (el usuario es `admin` por defecto). Por ejemplo: `APP_ADMIN_PASSWORD=<tu-contraseña-inicial> ./mvnw spring-boot:run`. La contraseña queda almacenada con BCrypt y no se sobrescribe si la cuenta ya existe. Cambia la contraseña inicial antes de desplegar en un entorno real.


· Ejecución Local
Requisitos Previos
JDK 23
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
