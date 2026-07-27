package com.clinic.clinic_management.application.doctor;

import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateDoctorUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;

    public CreateDoctorUseCase(DoctorRepositoryPort doctorRepositoryPort) {
        this.doctorRepositoryPort = doctorRepositoryPort;
    }

    public Doctor execute(String firstName, String lastName, String specialty, String email) {
        Doctor doctor = Doctor.create(firstName, lastName, specialty, email);
        return doctorRepositoryPort.save(doctor);
    }
}
