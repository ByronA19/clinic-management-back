# Clinic Management API

API para manejar una clínica: doctores, pacientes, especialidades, citas y usuarios. Tiene login con token (JWT), así que para usar casi cualquier endpoint primero hay que iniciar sesión.

## Con qué está hecho

- Java 17 + Spring Boot
- PostgreSQL como base de datos
- Spring Security con JWT para el login y la protección de endpoints
- Swagger para ver y probar la API desde el navegador
- Maven

## Qué necesitas para correrlo

- JDK 17 o superior
- PostgreSQL instalado y corriendo
- Crear una base de datos vacía llamada `clinic_management` (solo crearla, no hay que crear tablas ni nada más a mano)

## Instalación

###  Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd clinic-management
```

### Descargar las dependencias

```bash
./mvnw clean install
```

## Configuración

Antes de levantar el proyecto, revisa `src/main/resources/application.properties` y ajusta el usuario/contraseña de tu PostgreSQL local si no son los mismos:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clinic_management
spring.datasource.username=postgres
spring.datasource.password=root
```

La primera vez que corres el proyecto (si no hay usuarios en la base de datos), se crea automáticamente un usuario admin para poder entrar:

- **Email:** admin@clinic.com
- **Password:** Admin123!

> Nota: por ahora esas contraseñas y la clave del JWT están escritas directamente en `application.properties` para que sea más fácil de probar. Lo ideal a futuro sería moverlas a variables de entorno.

## La base de datos se crea sola

No hace falta crear tablas ni cargar datos a mano. Solo crea la base de datos vacía (`clinic_management`) y al correr el proyecto por primera vez pasa esto automáticamente:

1. Se crean todas las tablas (doctores, pacientes, especialidades, citas, usuarios).
2. Se crea el usuario admin para poder iniciar sesión.
3. Se cargan datos de ejemplo (especialidades, doctores, pacientes y citas) para que ya haya algo que ver y probar en Swagger, sin tener que registrar todo desde cero.

Esto solo pasa la primera vez (cuando las tablas están vacías). Si vuelves a correr el proyecto después, no se duplican los datos.

## Cómo correrlo

```bash
./mvnw spring-boot:run
```

Va a quedar disponible en `http://localhost:8080`.

## Tests

Le agregué pruebas unitarias a las partes más importantes: la validación de doctores, los casos de uso de crear/eliminar doctor, el login (credenciales buenas, malas, usuario inactivo) y la generación/verificación del token JWT.

Para correrlas:

```bash
./mvnw test
```

## Swagger (documentación interactiva)

Con el proyecto corriendo, entra a:

```
http://localhost:8080/swagger-ui/index.html
```

Ahí puedes ver todos los endpoints y probarlos directamente.

## Cómo autenticarse

Casi todos los endpoints piden un token en el header `Authorization`. Solo `/auth/login`, `/error` y Swagger están abiertos sin login.

**1. Iniciar sesión**

```
POST /auth/login
Content-Type: application/json

{
  "email": "admin@clinic.com",
  "password": "Admin123!"
}
```

Responde con:

```json
{ "token": "eyJhbGciOi..." }
```

**2. Usar ese token en el resto de las peticiones**

```
Authorization: Bearer eyJhbGciOi...
```

## Endpoints

| Método | Endpoint | Quién puede usarlo | Qué hace |
|---|---|---|---|
| POST | `/auth/login` | Cualquiera | Inicia sesión y devuelve el token |
| POST | `/doctors` | Solo admin | Crea un doctor |
| GET | `/doctors` | Con login | Lista los doctores |
| GET | `/doctors/{id}` | Con login | Trae un doctor por id |
| PUT | `/doctors/{id}` | Con login | Actualiza un doctor |
| DELETE | `/doctors/{id}` | Con login | Elimina un doctor |
| POST | `/patients` | Con login | Crea un paciente |
| GET | `/patients` | Con login | Lista los pacientes |
| GET | `/patients/{id}` | Con login | Trae un paciente por id |
| PUT | `/patients/{id}` | Con login | Actualiza un paciente |
| DELETE | `/patients/{id}` | Con login | Elimina un paciente |
| POST | `/specialties` | Con login | Crea una especialidad |
| GET | `/specialties` | Con login | Lista las especialidades |
| GET | `/specialties/{id}` | Con login | Trae una especialidad por id |
| PUT | `/specialties/{id}` | Con login | Actualiza una especialidad |
| DELETE | `/specialties/{id}` | Con login | Elimina una especialidad |
| POST | `/appointments` | Con login | Crea una cita |
| GET | `/appointments` | Con login | Lista las citas |
| GET | `/appointments/{id}` | Con login | Trae una cita por id |
| PUT | `/appointments/{id}` | Con login | Actualiza una cita |
| DELETE | `/appointments/{id}` | Con login | Elimina una cita |
| POST | `/users` | Solo admin | Crea un usuario |
| GET | `/users` | Solo admin | Lista los usuarios |
| GET | `/users/{id}` | Solo admin | Trae un usuario por id |
| PUT | `/users/{id}` | Solo admin | Actualiza un usuario |
| DELETE | `/users/{id}` | Solo admin | Elimina un usuario |

## Cómo está organizado el proyecto

Separé el código en tres capas para mantener la lógica de negocio independiente de Spring y la base de datos:

```
src/main/java/com/clinic/clinic_management/
├── domain/            # Las entidades (Doctor, Patient, etc.) y sus interfaces
├── application/       # La lógica de cada acción (crear, actualizar, eliminar...)
└── infrastructure/    # Los controllers, la conexión a la base de datos y la seguridad JWT
```

Más detalle en [HEXAGONAL_ARCHITECTURE_PHASE1.md](HEXAGONAL_ARCHITECTURE_PHASE1.md).
