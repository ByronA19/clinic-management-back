package com.clinic.clinic_management.infrastructure.web;

import com.clinic.clinic_management.application.specialty.CreateSpecialtyUseCase;
import com.clinic.clinic_management.application.specialty.DeleteSpecialtyUseCase;
import com.clinic.clinic_management.application.specialty.UpdateSpecialtyUseCase;
import com.clinic.clinic_management.domain.specialty.Specialty;
import com.clinic.clinic_management.domain.specialty.SpecialtyRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.CreateSpecialtyRequest;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateSpecialtyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specialties")
@Tag(name = "Especialidades", description = "Gestión de especialidades médicas")
@SecurityRequirement(name = "bearerAuth")
public class SpecialtyController {

    private final CreateSpecialtyUseCase createSpecialtyUseCase;
    private final UpdateSpecialtyUseCase updateSpecialtyUseCase;
    private final DeleteSpecialtyUseCase deleteSpecialtyUseCase;
    private final SpecialtyRepositoryPort specialtyRepositoryPort;

    public SpecialtyController(CreateSpecialtyUseCase createSpecialtyUseCase,
                                UpdateSpecialtyUseCase updateSpecialtyUseCase,
                                DeleteSpecialtyUseCase deleteSpecialtyUseCase,
                                SpecialtyRepositoryPort specialtyRepositoryPort) {
        this.createSpecialtyUseCase = createSpecialtyUseCase;
        this.updateSpecialtyUseCase = updateSpecialtyUseCase;
        this.deleteSpecialtyUseCase = deleteSpecialtyUseCase;
        this.specialtyRepositoryPort = specialtyRepositoryPort;
    }

    @PostMapping
    @Operation(summary = "Crear especialidad", description = "Registra una nueva especialidad médica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidad creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> createSpecialty(@Valid @RequestBody CreateSpecialtyRequest request) {
        Specialty specialty = createSpecialtyUseCase.execute(request.name(), request.description());
        return ResponseEntity.ok(Map.of("id", specialty.getId(), "message", "Especialidad creada"));
    }

    @GetMapping
    @Operation(summary = "Listar especialidades", description = "Obtiene todas las especialidades registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de especialidades")
    public ResponseEntity<List<Specialty>> listSpecialties() {
        return ResponseEntity.ok(specialtyRepositoryPort.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener especialidad", description = "Busca una especialidad por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<?> getSpecialty(@Parameter(description = "ID de la especialidad") @PathVariable Long id) {
        return specialtyRepositoryPort.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especialidad", description = "Actualiza los datos de una especialidad existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<?> updateSpecialty(@Parameter(description = "ID de la especialidad") @PathVariable Long id,
                                              @Valid @RequestBody UpdateSpecialtyRequest request) {
        Specialty specialty = updateSpecialtyUseCase.execute(id, request);
        return ResponseEntity.ok(Map.of("id", specialty.getId(), "message", "Especialidad actualizada"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar especialidad", description = "Elimina una especialidad por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidad eliminada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<?> deleteSpecialty(@Parameter(description = "ID de la especialidad") @PathVariable Long id) {
        deleteSpecialtyUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Especialidad eliminada"));
    }
}
