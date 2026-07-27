-- Datos de ejemplo para poder probar la API apenas se levanta el proyecto.
-- Solo se insertan si la tabla todavía está vacía, para no duplicar datos
-- cada vez que se reinicia la aplicación.

-- Especialidades
INSERT INTO specialties (name, description, active, created_at, updated_at)
SELECT * FROM (VALUES
    ('Medicina General', 'Consultas médicas generales', true, now(), now()),
    ('Cardiología', 'Diagnóstico y tratamiento de enfermedades del corazón', true, now(), now()),
    ('Pediatría', 'Atención médica para niños', true, now(), now()),
    ('Traumatología', 'Tratamiento de lesiones y fracturas', true, now(), now()),
    ('Dermatología', 'Diagnóstico y tratamiento de enfermedades de la piel', true, now(), now())
) AS v(name, description, active, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM specialties);

-- Doctores
INSERT INTO doctors (first_name, last_name, specialty, email, active, created_at, updated_at)
SELECT * FROM (VALUES
    ('Carlos', 'Martinez', 'Medicina General', 'carlos.martinez@clinic.com', true, now(), now()),
    ('Andrea', 'Torres', 'Cardiología', 'andrea.torres@clinic.com', true, now(), now()),
    ('Miguel', 'Salazar', 'Pediatría', 'miguel.salazar@clinic.com', true, now(), now()),
    ('Fernanda', 'Paredes', 'Traumatología', 'fernanda.paredes@clinic.com', true, now(), now()),
    ('Daniel', 'Vega', 'Dermatología', 'daniel.vega@clinic.com', true, now(), now())
) AS v(first_name, last_name, specialty, email, active, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM doctors);

-- Pacientes
INSERT INTO patients (first_name, last_name, documento, phone, email, created_at, updated_at)
SELECT * FROM (VALUES
    ('Juan', 'Perez', '1001001001', '0991111111', 'juan.perez@gmail.com', now(), now()),
    ('Maria', 'Lopez', '1001001002', '0992222222', 'maria.lopez@gmail.com', now(), now()),
    ('Carlos', 'Ruiz', '1001001003', '0993333333', 'carlos.ruiz@gmail.com', now(), now()),
    ('Ana', 'Mendoza', '1001001004', '0994444444', 'ana.mendoza@gmail.com', now(), now()),
    ('Luis', 'Gomez', '1001001005', '0995555555', 'luis.gomez@gmail.com', now(), now())
) AS v(first_name, last_name, documento, phone, email, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM patients);

-- Citas (relaciona pacientes y doctores por su email/documento ya insertados arriba.)
INSERT INTO appointments (patient_id, doctor_id, appointment_date, status, observations, created_at, updated_at)
SELECT * FROM (VALUES
    ((SELECT id FROM patients WHERE documento = '1001001001'), (SELECT id FROM doctors WHERE email = 'carlos.martinez@clinic.com'), TIMESTAMP '2026-07-27 08:30:00', 'PENDIENTE', 'Consulta inicial', now(), now()),
    ((SELECT id FROM patients WHERE documento = '1001001002'), (SELECT id FROM doctors WHERE email = 'andrea.torres@clinic.com'), TIMESTAMP '2026-07-27 09:30:00', 'CONFIRMADA', 'Seguimiento de presión arterial', now(), now()),
    ((SELECT id FROM patients WHERE documento = '1001001003'), (SELECT id FROM doctors WHERE email = 'miguel.salazar@clinic.com'), TIMESTAMP '2026-07-27 10:30:00', 'COMPLETADA', 'Visita pediátrica de rutina', now(), now()),
    ((SELECT id FROM patients WHERE documento = '1001001004'), (SELECT id FROM doctors WHERE email = 'fernanda.paredes@clinic.com'), TIMESTAMP '2026-07-27 11:00:00', 'PENDIENTE', 'Evaluación post-operatoria', now(), now()),
    ((SELECT id FROM patients WHERE documento = '1001001005'), (SELECT id FROM doctors WHERE email = 'daniel.vega@clinic.com'), TIMESTAMP '2026-07-27 14:00:00', 'CANCELADA', 'Paciente no disponible', now(), now())
) AS v(patient_id, doctor_id, appointment_date, status, observations, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM appointments);
