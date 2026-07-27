package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaDoctorRepositoryAdapter implements DoctorRepositoryPort {

    private final SpringDataDoctorRepository repository;
    private final DoctorMapper mapper;

    public JpaDoctorRepositoryAdapter(SpringDataDoctorRepository repository, DoctorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Doctor save(Doctor doctor) {
        LocalDateTime now = LocalDateTime.now();
        if (doctor.getCreatedAt() == null) {
            doctor.setCreatedAt(now);
        }
        if (doctor.getUpdatedAt() == null) {
            doctor.setUpdatedAt(now);
        }
        DoctorJpaEntity saved = repository.save(mapper.toJpaEntity(doctor));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Doctor> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
