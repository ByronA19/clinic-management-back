package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateSpecialtyRequest(
        @Schema(description = "Nombre de la especialidad", example = "Cardiología") @NotBlank String name,
        @Schema(description = "Descripción de la especialidad", example = "Diagnóstico y tratamiento de enfermedades del corazón") String description
) {
}
