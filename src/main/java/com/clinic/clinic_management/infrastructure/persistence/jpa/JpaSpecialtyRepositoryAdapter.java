package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.specialty.Specialty;
import com.clinic.clinic_management.domain.specialty.SpecialtyRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaSpecialtyRepositoryAdapter implements SpecialtyRepositoryPort {

    private final SpringDataSpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    public JpaSpecialtyRepositoryAdapter(SpringDataSpecialtyRepository repository, SpecialtyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Specialty save(Specialty specialty) {
        LocalDateTime now = LocalDateTime.now();
        if (specialty.getCreatedAt() == null) {
            specialty.setCreatedAt(now);
        }
        if (specialty.getUpdatedAt() == null) {
            specialty.setUpdatedAt(now);
        }
        SpecialtyJpaEntity saved = repository.save(mapper.toJpaEntity(specialty));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Specialty> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Specialty> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
