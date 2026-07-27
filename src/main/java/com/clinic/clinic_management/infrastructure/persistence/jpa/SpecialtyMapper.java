package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.specialty.Specialty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    Specialty toDomain(SpecialtyJpaEntity entity);
    SpecialtyJpaEntity toJpaEntity(Specialty specialty);
}
