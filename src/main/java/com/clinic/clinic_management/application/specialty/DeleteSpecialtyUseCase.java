package com.clinic.clinic_management.application.specialty;

import com.clinic.clinic_management.domain.specialty.SpecialtyRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeleteSpecialtyUseCase {

    private final SpecialtyRepositoryPort specialtyRepositoryPort;

    public DeleteSpecialtyUseCase(SpecialtyRepositoryPort specialtyRepositoryPort) {
        this.specialtyRepositoryPort = specialtyRepositoryPort;
    }

    public void execute(Long id) {
        specialtyRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especialidad no encontrada: " + id));
        specialtyRepositoryPort.deleteById(id);
    }
}
