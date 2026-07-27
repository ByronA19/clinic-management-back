package com.clinic.clinic_management.application.appointment;

import com.clinic.clinic_management.domain.appointment.Appointment;
import com.clinic.clinic_management.domain.appointment.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public CreateAppointmentUseCase(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    public Appointment execute(Long patientId, Long doctorId, LocalDateTime appointmentDate, String observations) {
        Appointment appointment = Appointment.create(patientId, doctorId, appointmentDate, observations);
        return appointmentRepositoryPort.save(appointment);
    }
}
