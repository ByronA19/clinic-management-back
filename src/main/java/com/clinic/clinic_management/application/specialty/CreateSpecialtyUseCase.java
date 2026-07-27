package com.clinic.clinic_management.application.specialty;

import com.clinic.clinic_management.domain.specialty.Specialty;
import com.clinic.clinic_management.domain.specialty.SpecialtyRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateSpecialtyUseCase {

    private final SpecialtyRepositoryPort specialtyRepositoryPort;

    public CreateSpecialtyUseCase(SpecialtyRepositoryPort specialtyRepositoryPort) {
        this.specialtyRepositoryPort = specialtyRepositoryPort;
    }

    public Specialty execute(String name, String description) {
        Specialty specialty = Specialty.create(name, description);
        return specialtyRepositoryPort.save(specialty);
    }
}
