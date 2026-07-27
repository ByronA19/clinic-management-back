package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Schema(description = "Nombre completo del usuario", example = "María López") String fullName,
        @Schema(description = "Correo electrónico", example = "maria.lopez@clinic.com") @Email String email,
        @Schema(description = "Contraseña (mínimo 6 caracteres)", example = "Secreta123!") @Size(min = 6) String password,
        @Schema(description = "Rol del usuario", example = "ADMIN") String role,
        @Schema(description = "Indica si el usuario está activo", example = "true") Boolean active
) {
}
