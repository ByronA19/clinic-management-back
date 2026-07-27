package com.clinic.clinic_management.infrastructure.web;

import com.clinic.clinic_management.application.appointment.CreateAppointmentUseCase;
import com.clinic.clinic_management.application.appointment.DeleteAppointmentUseCase;
import com.clinic.clinic_management.application.appointment.UpdateAppointmentUseCase;
import com.clinic.clinic_management.domain.appointment.Appointment;
import com.clinic.clinic_management.domain.appointment.AppointmentRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.CreateAppointmentRequest;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateAppointmentRequest;
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
@RequestMapping("/appointments")
@Tag(name = "Citas", description = "Gestión de citas médicas")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;
    private final DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public AppointmentController(CreateAppointmentUseCase createAppointmentUseCase,
                                  UpdateAppointmentUseCase updateAppointmentUseCase,
                                  DeleteAppointmentUseCase deleteAppointmentUseCase,
                                  AppointmentRepositoryPort appointmentRepositoryPort) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.updateAppointmentUseCase = updateAppointmentUseCase;
        this.deleteAppointmentUseCase = deleteAppointmentUseCase;
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @PostMapping
    @Operation(summary = "Crear cita", description = "Registra una nueva cita médica para un paciente con un doctor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = createAppointmentUseCase.execute(
                request.patientId(),
                request.doctorId(),
                request.appointmentDate(),
                request.observations()
        );
        return ResponseEntity.ok(Map.of("id", appointment.getId(), "message", "Cita creada"));
    }

    @GetMapping
    @Operation(summary = "Listar citas", description = "Obtiene todas las citas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de citas")
    public ResponseEntity<List<Appointment>> listAppointments() {
        return ResponseEntity.ok(appointmentRepositoryPort.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita", description = "Busca una cita por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> getAppointment(@Parameter(description = "ID de la cita") @PathVariable Long id) {
        return appointmentRepositoryPort.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita", description = "Actualiza los datos de una cita existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita actualizada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> updateAppointment(@Parameter(description = "ID de la cita") @PathVariable Long id,
                                                @Valid @RequestBody UpdateAppointmentRequest request) {
        Appointment appointment = updateAppointmentUseCase.execute(id, request);
        return ResponseEntity.ok(Map.of("id", appointment.getId(), "message", "Cita actualizada"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita", description = "Elimina una cita por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita eliminada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> deleteAppointment(@Parameter(description = "ID de la cita") @PathVariable Long id) {
        deleteAppointmentUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Cita eliminada"));
    }
}
