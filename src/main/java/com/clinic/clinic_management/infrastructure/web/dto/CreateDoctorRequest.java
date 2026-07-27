package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateDoctorRequest(
        @Schema(description = "Nombre del doctor", example = "Ana") @NotBlank String firstName,
        @Schema(description = "Apellido del doctor", example = "Gómez") @NotBlank String lastName,
        @Schema(description = "Especialidad del doctor", example = "Cardiología") String specialty,
        @Schema(description = "Correo electrónico", example = "ana.gomez@clinic.com") @Email String email
) {
}
