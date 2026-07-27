package com.clinic.clinic_management.application.appointment;

import com.clinic.clinic_management.domain.appointment.Appointment;
import com.clinic.clinic_management.domain.appointment.AppointmentRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateAppointmentRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UpdateAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public UpdateAppointmentUseCase(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    public Appointment execute(Long id, UpdateAppointmentRequest request) {
        Appointment appointment = appointmentRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró cita: " + id));

        if (request.patientId() != null) {
            appointment.setPatientId(request.patientId());
        }
        if (request.doctorId() != null) {
            appointment.setDoctorId(request.doctorId());
        }
        if (request.appointmentDate() != null) {
            appointment.setAppointmentDate(request.appointmentDate());
        }
        if (request.status() != null) {
            appointment.setStatus(request.status());
        }
        if (request.observations() != null) {
            appointment.setObservations(request.observations());
        }
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentRepositoryPort.save(appointment);
    }
}
