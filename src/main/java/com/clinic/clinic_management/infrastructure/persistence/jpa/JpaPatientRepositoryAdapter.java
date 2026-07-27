package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.patient.Patient;
import com.clinic.clinic_management.domain.patient.PatientRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaPatientRepositoryAdapter implements PatientRepositoryPort {

    private final SpringDataPatientRepository repository;
    private final PatientMapper mapper;

    public JpaPatientRepositoryAdapter(SpringDataPatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Patient save(Patient patient) {
        LocalDateTime now = LocalDateTime.now();
        if (patient.getCreatedAt() == null) {
            patient.setCreatedAt(now);
        }
        if (patient.getUpdatedAt() == null) {
            patient.setUpdatedAt(now);
        }
        PatientJpaEntity saved = repository.save(mapper.toJpaEntity(patient));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Patient> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
