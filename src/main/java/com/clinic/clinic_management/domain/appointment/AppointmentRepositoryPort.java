package com.clinic.clinic_management.domain.appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    void deleteById(Long id);
}
