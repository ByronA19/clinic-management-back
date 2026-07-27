package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.patient.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toDomain(PatientJpaEntity entity);
    PatientJpaEntity toJpaEntity(Patient patient);
}
