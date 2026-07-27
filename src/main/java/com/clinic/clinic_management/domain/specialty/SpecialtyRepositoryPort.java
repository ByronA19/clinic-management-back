package com.clinic.clinic_management.domain.specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyRepositoryPort {
    Specialty save(Specialty specialty);
    Optional<Specialty> findById(Long id);
    List<Specialty> findAll();
    void deleteById(Long id);
}
