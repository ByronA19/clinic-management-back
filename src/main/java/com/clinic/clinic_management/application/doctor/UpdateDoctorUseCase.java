package com.clinic.clinic_management.application.doctor;

import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateDoctorRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UpdateDoctorUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;

    public UpdateDoctorUseCase(DoctorRepositoryPort doctorRepositoryPort) {
        this.doctorRepositoryPort = doctorRepositoryPort;
    }

    public Doctor execute(Long id, UpdateDoctorRequest request) {
        Doctor doctor = doctorRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + id));

        if (request.firstName() != null) {
            doctor.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            doctor.setLastName(request.lastName());
        }
        if (request.specialty() != null) {
            doctor.setSpecialty(request.specialty());
        }
        if (request.email() != null) {
            doctor.setEmail(request.email());
        }
        if (request.active() != null) {
            doctor.setActive(request.active());
        }
        doctor.setUpdatedAt(LocalDateTime.now());

        return doctorRepositoryPort.save(doctor);
    }
}
