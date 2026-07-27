package com.clinic.clinic_management.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAppointmentRepository extends JpaRepository<AppointmentJpaEntity, Long> {
}
