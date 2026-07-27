package com.clinic.clinic_management.application.appointment;

import com.clinic.clinic_management.domain.appointment.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeleteAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public DeleteAppointmentUseCase(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    public void execute(Long id) {
        appointmentRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró cita: " + id));
        appointmentRepositoryPort.deleteById(id);
    }
}
