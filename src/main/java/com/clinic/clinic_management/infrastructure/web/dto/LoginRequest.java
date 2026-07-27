package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "Correo electrónico", example = "bnacato@example.com") @NotBlank @Email String email,
        @Schema(description = "Contraseña", example = "luis123") @NotBlank String password
) {
}
