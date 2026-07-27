package com.clinic.clinic_management.domain.patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepositoryPort {
    Patient save(Patient patient);
    Optional<Patient> findById(Long id);
    List<Patient> findAll();
    void deleteById(Long id);
}
