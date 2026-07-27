package com.clinic.clinic_management.application.specialty;

import com.clinic.clinic_management.domain.specialty.Specialty;
import com.clinic.clinic_management.domain.specialty.SpecialtyRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateSpecialtyRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UpdateSpecialtyUseCase {

    private final SpecialtyRepositoryPort specialtyRepositoryPort;

    public UpdateSpecialtyUseCase(SpecialtyRepositoryPort specialtyRepositoryPort) {
        this.specialtyRepositoryPort = specialtyRepositoryPort;
    }

    public Specialty execute(Long id, UpdateSpecialtyRequest request) {
        Specialty specialty = specialtyRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especialidad no encontrada:" + id));

        if (request.name() != null) {
            specialty.setName(request.name());
        }
        if (request.description() != null) {
            specialty.setDescription(request.description());
        }
        if (request.active() != null) {
            specialty.setActive(request.active());
        }
        specialty.setUpdatedAt(LocalDateTime.now());

        return specialtyRepositoryPort.save(specialty);
    }
}
