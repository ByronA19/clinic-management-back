package com.clinic.clinic_management.domain.doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepositoryPort {
    Doctor save(Doctor doctor);
    Optional<Doctor> findById(Long id);
    List<Doctor> findAll();
    void deleteById(Long id);
}
