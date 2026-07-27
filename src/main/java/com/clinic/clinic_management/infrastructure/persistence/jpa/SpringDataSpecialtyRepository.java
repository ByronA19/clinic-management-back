package com.clinic.clinic_management.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSpecialtyRepository extends JpaRepository<SpecialtyJpaEntity, Long> {
}
