# NavalWorks

API REST desarrollada con Spring Boot para la gestión de trabajos, bloques estructurales, planos isométricos y operarios del proyecto de las Fragatas F-110.

El backend no incluye interfaz gráfica. Las aplicaciones cliente, como Android o escritorio, consumen esta API y muestran la información al usuario.

## Funcionalidades

- Gestión de bloques estructurales de la fragata.
- Gestión de isométricos asociados a bloques.
- Carga, reemplazo y descarga de planos PDF.
- Creación y asignación de trabajos.
- Asociación de operarios a trabajos.
- Filtrado de trabajos por isométrico, estado y operario.
- Control de estados y permisos según el rol del usuario.
- Autenticación HTTP Basic sin estado.
- Cuenta administradora configurable mediante variables de entorno.
- Contraseñas cifradas con BCrypt.

## Cambios recientes

### Isométricos

- Los isométricos solo pueden tener el estado `PUBLICADO`.
- Ya no se permiten isométricos sin PDF.
- La creación se realiza mediante `multipart/form-data` y guarda primero el archivo PDF.
- Si falla el guardado del archivo o de la entidad, no queda un isométrico huérfano.
- Los archivos se almacenan con nombre UUID en `uploads/isometricos`.
- Se puede reemplazar el PDF de un isométrico existente.
- Un isométrico no se puede eliminar si tiene trabajos asociados; la API responde `409 Conflict`.

### Trabajos

- Un isométrico solo puede estar asociado a un trabajo.
- Crear un trabajo con un isométrico ya utilizado responde `409 Conflict`.
- Actualizar un trabajo impide asignarlo a un isométrico usado por otro trabajo.
- No se permite actualizar un trabajo a `PENDIENTE` si tiene operarios asignados.
- Al crear un trabajo sin operarios, queda en `PENDIENTE`.
- Al crear un trabajo con operarios, queda en `EN_EJECUCION`.
- Al asignar el primer operario a un trabajo pendiente, pasa automáticamente a `EN_EJECUCION`.
- El administrador puede eliminar trabajos y sus asignaciones de operarios.

### Seguridad y operarios

- Las consultas `GET` requieren autenticación con los roles `ADMINISTRADOR` u `OPERARIO`.
- Las operaciones de escritura requieren el rol `ADMINISTRADOR`.
- El administrador puede eliminar isométricos y trabajos.
- Administradores y operarios pueden añadir o retirar operarios de un trabajo.
- Un operario puede recibir usuario, contraseña y permisos de administrador al crearlo.
- Las contraseñas se almacenan cifradas y no se devuelven en los DTO.
- Se usa autenticación HTTP Basic con sesiones deshabilitadas.

## Arquitectura

El proyecto está organizado en capas:

- `controller`: exposición de endpoints REST.
- `service`: lógica de negocio y validaciones.
- `repository`: acceso a datos con Spring Data JPA.
- `model`: entidades persistentes.
- `dto`: objetos de entrada y salida de la API.
- `enums/model`: especialistas y estados del dominio.
- `config`: seguridad, cuenta administradora y configuración web.

La jerarquía principal es:

`Bloque -> Isométrico -> Trabajo -> Operario`

## Tecnologías

- Java 23
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- Spring Security
- Spring Validation
- MySQL 8
- Maven
- Lombok
- BCrypt

## Modelo de dominio

### Bloque

Representa una sección estructural de la fragata.

### Isométrico

Plano de tuberías asociado a un bloque. Contiene un código, el estado `PUBLICADO` y la ruta de su archivo PDF.

### Trabajo

Orden de montaje o soldadura asociada a un único isométrico y a cero o varios operarios.

Estados disponibles:

- `PENDIENTE`
- `EN_EJECUCION`
- `REVISADO`
- `ACEPTADO`

### Operario

Personal técnico del astillero. Especialidades disponibles:

- `TUBERO`
- `SOLDADOR`
- `INSPECTOR_CALIDAD`

## API REST

La URL base local es `http://localhost:8080`.

### Autenticación

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| `POST` | `/api/auth/admin` | Comprueba las credenciales y el rol administrador | Público |

Las restantes peticiones protegidas utilizan HTTP Basic:

```bash
curl -u usuario:contraseña http://localhost:8080/api/bloques
```

### Bloques

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/bloques` | Lista todos los bloques |
| `GET` | `/api/bloques/{id}` | Obtiene un bloque |
| `GET` | `/api/bloques/codigo/{codigo}` | Busca por código |
| `POST` | `/api/bloques` | Crea un bloque |
| `PUT` | `/api/bloques/{id}` | Actualiza un bloque |
| `DELETE` | `/api/bloques/{id}` | Elimina un bloque |

### Isométricos

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/isometricos` | Lista los isométricos publicados |
| `GET` | `/api/isometricos/admin` | Lista todos los isométricos para administración |
| `GET` | `/api/isometricos/{id}` | Obtiene un isométrico |
| `GET` | `/api/isometricos/codigo/{codigo}` | Busca por código |
| `GET` | `/api/isometricos/bloque/{bloqueId}` | Lista los isométricos de un bloque |
| `GET` | `/api/isometricos/{id}/pdf` | Descarga o muestra el PDF asociado |
| `POST` | `/api/isometricos` | Crea un isométrico con PDF |
| `POST` | `/api/isometricos/{id}/pdf` | Carga o reemplaza el PDF |
| `PUT` | `/api/isometricos/{id}` | Actualiza código y bloque |
| `DELETE` | `/api/isometricos/{id}` | Elimina si no tiene trabajos asociados |

Ejemplo de creación:

```bash
curl -u admin:contraseña \
  -F "codigoIsometrico=B322-ISO-001" \
  -F "bloqueId=1" \
  -F "file=@plano.pdf" \
  http://localhost:8080/api/isometricos
```

Solo se aceptan archivos cuyo nombre termine en `.pdf`. El límite configurado es de 20 MB por archivo y petición.

### Trabajos

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/trabajos` | Lista todos los trabajos |
| `GET` | `/api/trabajos/{id}` | Obtiene un trabajo |
| `GET` | `/api/trabajos/isometrico/{isometricoId}` | Filtra por isométrico |
| `GET` | `/api/trabajos/estado/{estado}` | Filtra por estado |
| `GET` | `/api/trabajos/operario/{operarioId}` | Filtra por operario |
| `POST` | `/api/trabajos` | Crea un trabajo |
| `PUT` | `/api/trabajos/{id}` | Actualiza un trabajo |
| `POST` | `/api/trabajos/{id}/operarios/{operarioId}` | Añade un operario |
| `DELETE` | `/api/trabajos/{id}/operarios/{operarioId}` | Retira un operario |
| `DELETE` | `/api/trabajos/{id}` | Elimina un trabajo |

### Operarios

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/operarios` | Lista todos los operarios |
| `GET` | `/api/operarios/{id}` | Obtiene un operario |
| `GET` | `/api/operarios/especialista/{especialista}` | Filtra por especialidad |
| `GET` | `/api/operarios/nombre/{nombre}` | Filtra por nombre |
| `GET` | `/api/operarios/apellidos/{apellidos}` | Filtra por apellidos |
| `POST` | `/api/operarios` | Crea un operario |
| `PUT` | `/api/operarios/{id}` | Actualiza un operario |
| `DELETE` | `/api/operarios/{id}` | Elimina un operario |

Ejemplo de creación de operario administrador:

```json
{
  "nombre": "Jorge",
  "apellidos": "Lesta",
  "especialista": "TUBERO",
  "usuario": "j.lesta",
  "password": "contraseña-segura",
  "administrador": 1
}
```

El campo `administrador` admite `0` para operario normal y `1` para administrador.

## Configuración local

### Requisitos

- JDK 23
- Maven 3.8 o posterior
- MySQL 8

### Base de datos

La configuración predeterminada usa:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Works_Csmt?serverTimezone=Europe/Madrid&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME:root}
spring.jpa.hibernate.ddl-auto=update
```

Configura la contraseña de MySQL mediante `DB_PASSWORD` o en `application.properties`.

### Cuentas de acceso

Al arrancar, si se configura `APP_ADMIN_PASSWORD`, la aplicación busca la cuenta
indicada: si no existe, la crea; si ya existe, conserva su contraseña y asegura
que tenga rol de administrador. La cuenta se guarda en la base de datos:

```bash
APP_ADMIN_USERNAME=admin \
APP_ADMIN_PASSWORD='contraseña-inicial' \
./mvnw spring-boot:run
```

Las variables disponibles son:

- `APP_ADMIN_USERNAME`: nombre de usuario, `admin` por defecto.
- `APP_ADMIN_PASSWORD`: necesaria para crear la cuenta administradora inicial.
- `DB_USERNAME`: usuario de MySQL, `root` por defecto.
- `DB_PASSWORD`: contraseña de MySQL.

Un administrador autenticado puede crear cuentas adicionales mediante
`POST /api/operarios`. En el JSON, `administrador: 1` crea un administrador;
`administrador: 0` junto con `usuario` y `password` crea un encargado; con
`administrador: 0` y sin credenciales crea un operario sin acceso de inicio de
sesión. Las contraseñas se guardan codificadas. El endpoint `POST /api/auth/login`
valida las credenciales y devuelve el rol (`ADMINISTRADOR` o `ENCARGADO`).

Ejemplo de alta de encargado (para administrador, cambia `administrador` a `1`):

```json
{
  "nombre": "Ana",
  "apellidos": "Lopez",
  "especialista": "TUBERO",
  "administrador": 0,
  "usuario": "ana",
  "password": "una-contraseña-segura"
}
```

Este repositorio contiene el backend, no la interfaz de la app; la pantalla para
seleccionar el tipo de cuenta debe enviar esos campos a `POST /api/operarios`.

Si `APP_ADMIN_PASSWORD` no está definida, no se crea ni modifica la cuenta administradora.

## Ejecución

Desde la raíz del proyecto:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

La aplicación se ejecutará en:

```text
http://localhost:8080
```

## Pruebas

Ejecuta la suite del proyecto con:

```bash
./mvnw test
```

## Autor

Jorge Lesta - Desarrollador Backend y estudiante de Desarrollo de Aplicaciones Multiplataforma.
