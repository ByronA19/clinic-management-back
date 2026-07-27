package com.clinic.clinic_management.application.patient;

import com.clinic.clinic_management.domain.patient.Patient;
import com.clinic.clinic_management.domain.patient.PatientRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreatePatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public CreatePatientUseCase(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    public Patient execute(String firstName, String lastName, String documento, String phone, String email) {
        Patient patient = Patient.create(firstName, lastName, documento, phone, email);
        return patientRepositoryPort.save(patient);
    }
}
