package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record UpdateDoctorRequest(
        @Schema(description = "Nombre del doctor", example = "Ana") String firstName,
        @Schema(description = "Apellido del doctor", example = "Gómez") String lastName,
        @Schema(description = "Especialidad del doctor", example = "Cardiología") String specialty,
        @Schema(description = "Correo electrónico", example = "ana.gomez@clinic.com") @Email String email,
        @Schema(description = "Indica si el doctor está activo", example = "true") Boolean active
) {
}
