package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.doctor.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toDomain(DoctorJpaEntity entity);
    DoctorJpaEntity toJpaEntity(Doctor doctor);
}
