package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UpdateAppointmentRequest(
        @Schema(description = "ID del paciente", example = "1") Long patientId,
        @Schema(description = "ID del doctor", example = "2") Long doctorId,
        @Schema(description = "Fecha y hora de la cita", example = "2026-08-01T10:30:00") LocalDateTime appointmentDate,
        @Schema(description = "Estado de la cita", example = "CONFIRMED") String status,
        @Schema(description = "Observaciones adicionales", example = "Control de rutina") String observations
) {
}
