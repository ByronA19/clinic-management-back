package com.clinic.clinic_management.infrastructure.web;

import com.clinic.clinic_management.application.patient.CreatePatientUseCase;
import com.clinic.clinic_management.application.patient.DeletePatientUseCase;
import com.clinic.clinic_management.application.patient.UpdatePatientUseCase;
import com.clinic.clinic_management.domain.patient.Patient;
import com.clinic.clinic_management.domain.patient.PatientRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.CreatePatientRequest;
import com.clinic.clinic_management.infrastructure.web.dto.UpdatePatientRequest;
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
@RequestMapping("/patients")
@Tag(name = "Pacientes", description = "Gestión de pacientes de la clínica")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final DeletePatientUseCase deletePatientUseCase;
    private final PatientRepositoryPort patientRepositoryPort;

    public PatientController(CreatePatientUseCase createPatientUseCase,
                              UpdatePatientUseCase updatePatientUseCase,
                              DeletePatientUseCase deletePatientUseCase,
                              PatientRepositoryPort patientRepositoryPort) {
        this.createPatientUseCase = createPatientUseCase;
        this.updatePatientUseCase = updatePatientUseCase;
        this.deletePatientUseCase = deletePatientUseCase;
        this.patientRepositoryPort = patientRepositoryPort;
    }

    @PostMapping
    @Operation(summary = "Crear paciente", description = "Registra un nuevo paciente en la clínica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        Patient patient = createPatientUseCase.execute(
                request.firstName(),
                request.lastName(),
                request.documento(),
                request.phone(),
                request.email()
        );
        return ResponseEntity.ok(Map.of("id", patient.getId(), "message", "Paciente creado"));
    }

    @GetMapping
    @Operation(summary = "Listar pacientes", description = "Obtiene todos los pacientes registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de pacientes")
    public ResponseEntity<List<Patient>> listPatients() {
        return ResponseEntity.ok(patientRepositoryPort.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente", description = "Busca un paciente por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<?> getPatient(@Parameter(description = "ID del paciente") @PathVariable Long id) {
        return patientRepositoryPort.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente", description = "Actualiza los datos de un paciente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente actualizado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<?> updatePatient(@Parameter(description = "ID del paciente") @PathVariable Long id,
                                            @Valid @RequestBody UpdatePatientRequest request) {
        Patient patient = updatePatientUseCase.execute(id, request);
        return ResponseEntity.ok(Map.of("id", patient.getId(), "message", "Paciente actualizado"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente eliminado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<?> deletePatient(@Parameter(description = "ID del paciente") @PathVariable Long id) {
        deletePatientUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Paciente eliminado"));
    }
}
