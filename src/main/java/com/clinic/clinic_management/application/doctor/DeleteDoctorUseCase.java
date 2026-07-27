package com.clinic.clinic_management.application.doctor;

import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeleteDoctorUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;

    public DeleteDoctorUseCase(DoctorRepositoryPort doctorRepositoryPort) {
        this.doctorRepositoryPort = doctorRepositoryPort;
    }

    public void execute(Long id) {
        doctorRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor no encontrado: " + id));
        doctorRepositoryPort.deleteById(id);
    }
}
