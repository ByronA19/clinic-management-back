package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.appointment.Appointment;
import com.clinic.clinic_management.domain.appointment.AppointmentRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private final SpringDataAppointmentRepository repository;
    private final AppointmentMapper mapper;

    public JpaAppointmentRepositoryAdapter(SpringDataAppointmentRepository repository, AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        LocalDateTime now = LocalDateTime.now();
        if (appointment.getCreatedAt() == null) {
            appointment.setCreatedAt(now);
        }
        if (appointment.getUpdatedAt() == null) {
            appointment.setUpdatedAt(now);
        }
        AppointmentJpaEntity saved = repository.save(mapper.toJpaEntity(appointment));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
