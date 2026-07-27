package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        @Schema(description = "ID del paciente", example = "1") @NotNull Long patientId,
        @Schema(description = "ID del doctor", example = "2") @NotNull Long doctorId,
        @Schema(description = "Fecha y hora de la cita", example = "2026-08-01T10:30:00") @NotNull LocalDateTime appointmentDate,
        @Schema(description = "Observaciones adicionales", example = "Control de rutina") String observations
) {
}
