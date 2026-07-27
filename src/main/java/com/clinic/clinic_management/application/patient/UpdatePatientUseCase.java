package com.clinic.clinic_management.application.patient;

import com.clinic.clinic_management.domain.patient.Patient;
import com.clinic.clinic_management.domain.patient.PatientRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.UpdatePatientRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UpdatePatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public UpdatePatientUseCase(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    public Patient execute(Long id, UpdatePatientRequest request) {
        Patient patient = patientRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found: " + id));

        if (request.firstName() != null) {
            patient.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            patient.setLastName(request.lastName());
        }
        if (request.documento() != null) {
            patient.setDocumento(request.documento());
        }
        if (request.phone() != null) {
            patient.setPhone(request.phone());
        }
        if (request.email() != null) {
            patient.setEmail(request.email());
        }
        patient.setUpdatedAt(LocalDateTime.now());

        return patientRepositoryPort.save(patient);
    }
}
