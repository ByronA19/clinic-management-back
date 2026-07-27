package com.clinic.clinic_management.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateSpecialtyRequest(
        @Schema(description = "Nombre de la especialidad", example = "Cardiología") String name,
        @Schema(description = "Descripción de la especialidad", example = "Diagnóstico y tratamiento de enfermedades del corazón") String description,
        @Schema(description = "Indica si la especialidad está activa", example = "true") Boolean active
) {
}
