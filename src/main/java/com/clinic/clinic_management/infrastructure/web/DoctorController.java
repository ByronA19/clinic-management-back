package com.clinic.clinic_management.infrastructure.web;

import com.clinic.clinic_management.application.doctor.CreateDoctorUseCase;
import com.clinic.clinic_management.application.doctor.DeleteDoctorUseCase;
import com.clinic.clinic_management.application.doctor.UpdateDoctorUseCase;
import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.CreateDoctorRequest;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateDoctorRequest;
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
@RequestMapping("/doctors")
@Tag(name = "Doctores", description = "Gestión de doctores de la clínica")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {

    private final CreateDoctorUseCase createDoctorUseCase;
    private final UpdateDoctorUseCase updateDoctorUseCase;
    private final DeleteDoctorUseCase deleteDoctorUseCase;
    private final DoctorRepositoryPort doctorRepositoryPort;

    public DoctorController(CreateDoctorUseCase createDoctorUseCase,
                             UpdateDoctorUseCase updateDoctorUseCase,
                             DeleteDoctorUseCase deleteDoctorUseCase,
                             DoctorRepositoryPort doctorRepositoryPort) {
        this.createDoctorUseCase = createDoctorUseCase;
        this.updateDoctorUseCase = updateDoctorUseCase;
        this.deleteDoctorUseCase = deleteDoctorUseCase;
        this.doctorRepositoryPort = doctorRepositoryPort;
    }

    @PostMapping
    @Operation(summary = "Crear doctor", description = "Registra un nuevo doctor en la clínica. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos (requiere rol ADMIN)")
    })
    public ResponseEntity<?> createDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        Doctor doctor = createDoctorUseCase.execute(
                request.firstName(),
                request.lastName(),
                request.specialty(),
                request.email()
        );
        return ResponseEntity.ok(Map.of("id", doctor.getId(), "message", "Doctor creado"));
    }

    @GetMapping
    @Operation(summary = "Listar doctores", description = "Obtiene todos los doctores registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de doctores")
    public ResponseEntity<List<Doctor>> listDoctors() {
        return ResponseEntity.ok(doctorRepositoryPort.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener doctor", description = "Busca un doctor por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    public ResponseEntity<?> getDoctor(@Parameter(description = "ID del doctor") @PathVariable Long id) {
        return doctorRepositoryPort.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar doctor", description = "Actualiza los datos de un doctor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor actualizado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    public ResponseEntity<?> updateDoctor(@Parameter(description = "ID del doctor") @PathVariable Long id,
                                           @Valid @RequestBody UpdateDoctorRequest request) {
        Doctor doctor = updateDoctorUseCase.execute(id, request);
        return ResponseEntity.ok(Map.of("id", doctor.getId(), "message", "Doctor actualizado"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar doctor", description = "Elimina un doctor por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor eliminado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    public ResponseEntity<?> deleteDoctor(@Parameter(description = "ID del doctor") @PathVariable Long id) {
        deleteDoctorUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Doctor eliminado"));
    }
}
