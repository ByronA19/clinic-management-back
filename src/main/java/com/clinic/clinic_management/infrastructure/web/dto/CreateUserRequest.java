package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @Schema(description = "Nombre completo del usuario", example = "María López") @NotBlank String fullName,
        @Schema(description = "Correo electrónico", example = "maria.lopez@clinic.com") @NotBlank @Email String email,
        @Schema(description = "Contraseña (mínimo 6 caracteres)", example = "Secreta123!") @NotBlank @Size(min = 6) String password,
        @Schema(description = "Rol del usuario", example = "ADMIN") @NotBlank String role
) {
}
