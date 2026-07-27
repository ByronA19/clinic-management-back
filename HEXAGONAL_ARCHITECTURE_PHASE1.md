# Arquitectura hexagonal — Fase 1

## 1. Idea central
Separar el negocio de la infraestructura para que el dominio sea independiente de:
- bases de datos
- TypeORM
- controladores HTTP
- frameworks de autenticación

La regla principal es:
- el dominio no debe depender de TypeORM ni de Express/Nest
- los adaptadores sí dependen del dominio

---

## 2. Capas recomendadas

### Capa de dominio
Contiene la lógica de negocio real.

Incluye:
- entidades
- value objects
- reglas de negocio
- puertos (interfaces)

Ejemplo:
- Patient
- Doctor
- Appointment
- User
- Specialty

### Capa de aplicación
Orquesta los casos de uso.

Incluye:
- servicios de aplicación
- casos de uso
- DTOs de entrada/salida

Ejemplo:
- CreatePatientUseCase
- ScheduleAppointmentUseCase
- AuthenticateUserUseCase

### Capa de infraestructura
Implementa los puertos y conecta con el exterior.

Incluye:
- repositorios TypeORM
- controladores REST
- adaptadores de autenticación
- adaptadores de correo o notificaciones

---

## 3. Estructura recomendada para la Fase 1

```text
src/
  domain/
    users/
      entities/
      repositories/
      services/
    patients/
      entities/
      repositories/
    doctors/
      entities/
      repositories/
    specialties/
      entities/
      repositories/
    appointments/
      entities/
      repositories/

  application/
    usecases/
      users/
      patients/
      doctors/
      appointments/

  infrastructure/
    persistence/
      typeorm/
        entities/
        repositories/
    web/
      controllers/
      dto/
    auth/
```

---

## 4. Primera fase mínima
Para no cargar demasiado, implenta primero estos módulos:

1. Users
2. Patients
3. Doctors
4. Specialties
5. Appointments

Eso permite cubrir el flujo principal de una clínica.

---

## 5. Mapa de responsabilidades

### Dominio
- User: reglas de autenticación y rol
- Patient: validación de datos básicos
- Doctor: validación de especialidad y estado
- Specialty: catálogo base
- Appointment: reglas de disponibilidad y estado

### Aplicación
- crear paciente
- crear médico
- crear especialidad
- agendar cita
- cambiar estado de cita
- autenticar usuario

### Infraestructura
- TypeORM repositories
- controllers REST
- validación HTTP
- persistencia en PostgreSQL

---

## 6. Puertos y adaptadores

### Puertos del dominio
Ejemplos:
- PatientRepository
- DoctorRepository
- AppointmentRepository
- UserRepository

### Adaptadores de infraestructura
Ejemplos:
- TypeOrmPatientRepository
- TypeOrmDoctorRepository
- TypeOrmAppointmentRepository
- PatientController
- AuthController

Esto permite cambiar la base de datos más adelante sin tocar el dominio.

---

## 7. Flujo recomendado de implementación

### Paso 1 — Dominio
Crear entidades y reglas básicas.

### Paso 2 — Puertos
Definir interfaces de repositorio.

### Paso 3 — Casos de uso
Implementar casos de uso de negocio.

### Paso 4 — Adaptadores TypeORM
Implementar repositorios usando TypeORM.

### Paso 5 — Controladores
Exponer endpoints REST.

---

## 8. Ejemplo de diseño inicial

### Entidad de dominio: Patient
- id
- firstName
- lastName
- dni
- email
- phone
- birthDate
- address
- createdAt
- updatedAt

### Caso de uso: CreatePatientUseCase
Recibe un DTO y delega a un repositorio.

### Adaptador: TypeOrmPatientRepository
Implementa PatientRepository y persiste en PostgreSQL.

---

## 9. Recomendación práctica
Para la primera fase, evita sobre-diseñar:
- no agregues pagos ni historiales clínicos todavía
- no mezcles lógica de infraestructura con lógica de negocio
- mantén los casos de uso muy simples

---

## 10. Siguiente paso ideal
El siguiente paso es convertir esta propuesta en:
1. entidades de dominio
2. interfaces de repositorio
3. casos de uso base
4. adaptadores TypeORM

Con eso ya tendrás la base hexagonal lista para crecer.
