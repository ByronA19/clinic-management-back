package com.clinic.clinic_management.infrastructure.persistence.jpa;

import com.clinic.clinic_management.domain.appointment.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toDomain(AppointmentJpaEntity entity);
    AppointmentJpaEntity toJpaEntity(Appointment appointment);
}
