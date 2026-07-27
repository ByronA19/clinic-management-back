package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record UpdatePatientRequest(
        @Schema(description = "Nombre del paciente", example = "Juan") String firstName,
        @Schema(description = "Apellido del paciente", example = "Pérez") String lastName,
        @Schema(description = "Documento de identidad", example = "1234567890") String documento,
        @Schema(description = "Teléfono de contacto", example = "+593987654321") String phone,
        @Schema(description = "Correo electrónico", example = "juan.perez@example.com") @Email String email
) {
}
