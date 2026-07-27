package com.clinic.clinic_management.domain.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentDate;
    private String status;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Appointment create(Long patientId, Long doctorId, LocalDateTime appointmentDate, String observations) {
        if (patientId == null) {
            throw new IllegalArgumentException("patientId is required");
        }
        if (doctorId == null) {
            throw new IllegalArgumentException("doctorId is required");
        }
        if (appointmentDate == null) {
            throw new IllegalArgumentException("appointmentDate is required");
        }
        LocalDateTime now = LocalDateTime.now();
        return new Appointment(null, patientId, doctorId, appointmentDate, "PENDIENTE", observations, now, now);
    }
}
