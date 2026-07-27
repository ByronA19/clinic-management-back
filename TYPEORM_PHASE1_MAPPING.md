# Mapeo inicial para TypeORM — Fase 1

## Objetivo de la fase 1
Implementar el núcleo del sistema de clínica con un enfoque simple, estable y listo para crecer:
- autenticación y usuarios
- pacientes
- médicos y especialidades
- citas
- historial clínico básico

## 1. Casos de negocio a cubrir

### 1.1 Autenticación y usuarios
**Caso:** registrar usuarios del sistema y permitir acceso.

**Entidad recomendada:**
- User

**Campos básicos:**
- id
- fullName
- email
- passwordHash
- role
- isActive
- createdAt
- updatedAt

**Relaciones:**
- Un usuario puede tener un rol
- Un usuario puede crear/editar citas (según rol)

**Operaciones base:**
- crear usuario
- login
- cambiar contraseña
- listar usuarios

---

### 1.2 Gestión de pacientes
**Caso:** registrar, editar y consultar pacientes.

**Entidad recomendada:**
- Patient

**Campos básicos:**
- id
- firstName
- lastName
- dni
- phone
- email
- birthDate
- address
- gender
- createdAt
- updatedAt

**Relaciones:**
- Un paciente puede tener muchas citas
- Un paciente puede tener muchos historiales clínicos

**Operaciones base:**
- crear paciente
- editar paciente
- listar pacientes
- buscar paciente por DNI o nombre

---

### 1.3 Gestión de médicos
**Caso:** registrar médicos y agruparlos por especialidad.

**Entidad recomendada:**
- Doctor

**Campos básicos:**
- id
- firstName
- lastName
- specialtyId
- licenseNumber
- phone
- email
- isActive
- createdAt
- updatedAt

**Relaciones:**
- Un médico pertenece a una especialidad
- Un médico puede tener muchas citas

**Operaciones base:**
- crear médico
- editar médico
- listar médicos
- filtrar por especialidad

---

### 1.4 Especialidades médicas
**Caso:** catalogar especialidades para asignar a médicos.

**Entidad recomendada:**
- Specialty

**Campos básicos:**
- id
- name
- description
- isActive

**Relaciones:**
- Una especialidad tiene muchos médicos

**Operaciones base:**
- crear especialidad
- editar especialidad
- listar especialidades

---

### 1.5 Gestión de citas
**Caso:** agendar, consultar y cancelar citas.

**Entidad recomendada:**
- Appointment

**Campos básicos:**
- id
- patientId
- doctorId
- scheduledAt
- status
- notes
- createdAt
- updatedAt

**Estados recomendados:**
- pending
- confirmed
- completed
- cancelled

**Relaciones:**
- Una cita pertenece a un paciente
- Una cita pertenece a un médico

**Operaciones base:**
- crear cita
- actualizar estado
- listar citas por fecha
- listar citas por médico o paciente
- cancelar cita

---

### 1.6 Historial clínico
**Caso:** guardar observaciones y evolución del paciente.

**Entidad recomendada:**
- MedicalRecord

**Campos básicos:**
- id
- patientId
- doctorId
- appointmentId
- diagnosis
- treatment
- observations
- createdAt

**Relaciones:**
- Un historial pertenece a un paciente
- Un historial puede estar asociado a una cita
- Un historial puede ser creado por un médico

**Operaciones base:**
- crear registro
- listar registros por paciente
- editar registro

---

### 1.7 Pagos y facturación básica
**Caso:** registrar pagos asociados a citas o servicios.

**Entidad recomendada:**
- Payment

**Campos básicos:**
- id
- appointmentId
- amount
- paymentMethod
- status
- paidAt
- createdAt

**Relaciones:**
- Un pago pertenece a una cita

**Operaciones base:**
- registrar pago
- listar pagos
- marcar como pagado

---

## 2. Mapa de entidades y relaciones

```text
User 1---1 Role
Patient 1---N Appointment
Doctor 1---N Appointment
Doctor N---1 Specialty
Patient 1---N MedicalRecord
Appointment 1---1 MedicalRecord
Appointment 1---1 Payment
```

## 3. Orden recomendado de implementación

### Fase 1A — Base del sistema
1. User
2. Role
3. Specialty
4. Doctor
5. Patient

### Fase 1B — Operaciones clínicas
6. Appointment
7. MedicalRecord
8. Payment

## 4. Recomendación de estructura de módulos

```text
src/
  modules/
    auth/
    users/
    patients/
    doctors/
    specialties/
    appointments/
    medical-records/
    payments/
```

## 5. Recomendación de endpoints iniciales

### Auth
- POST /auth/login
- POST /auth/register

### Patients
- GET /patients
- GET /patients/:id
- POST /patients
- PUT /patients/:id

### Doctors
- GET /doctors
- GET /doctors/:id
- POST /doctors
- PUT /doctors/:id

### Appointments
- GET /appointments
- GET /appointments/:id
- POST /appointments
- PUT /appointments/:id
- PATCH /appointments/:id/status

### Medical records
- GET /patients/:id/medical-records
- POST /medical-records

### Payments
- GET /payments
- POST /payments

## 6. Recomendación de TypeORM para esta fase
- usar `@Entity()` para cada tabla
- usar `@PrimaryGeneratedColumn()` para ids
- usar `@Column()` para campos simples
- usar `@ManyToOne()` y `@OneToMany()` para relaciones
- usar migraciones desde el inicio
- definir `dataSource` centralizado para evitar duplicación

## 7. Siguiente paso recomendado
Implementar primero estas 5 entidades como base mínima:
1. User
2. Patient
3. Doctor
4. Specialty
5. Appointment

Con eso ya se puede cubrir el flujo principal de una clínica: registrar pacientes, asignar médicos, agendar citas y dar seguimiento.
