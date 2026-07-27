package com.clinic.clinic_management.application.patient;

import com.clinic.clinic_management.domain.patient.PatientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeletePatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public DeletePatientUseCase(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    public void execute(Long id) {
        patientRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado: " + id));
        patientRepositoryPort.deleteById(id);
    }
}
