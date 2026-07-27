package com.clinic.clinic_management.infrastructure.web;

import com.clinic.clinic_management.application.user.CreateUserUseCase;
import com.clinic.clinic_management.application.user.DeleteUserUseCase;
import com.clinic.clinic_management.application.user.UpdateUserUseCase;
import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.CreateUserRequest;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateUserRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema (requiere rol ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserRepositoryPort userRepositoryPort;

    public UserController(CreateUserUseCase createUserUseCase,
                           UpdateUserUseCase updateUserUseCase,
                           DeleteUserUseCase deleteUserUseCase,
                           UserRepositoryPort userRepositoryPort) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userRepositoryPort = userRepositoryPort;
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.execute(
                request.fullName(),
                request.email(),
                request.password(),
                request.role()
        );
        return ResponseEntity.ok(Map.of("id", user.getId(), "message", "Usuario creado"));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados (sin contraseñas).")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios")
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        return ResponseEntity.ok(userRepositoryPort.findAll().stream()
                .map(UserController::toSafeMap)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Busca un usuario por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> getUser(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        return userRepositoryPort.findById(id)
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(toSafeMap(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> updateUser(@Parameter(description = "ID del usuario") @PathVariable Long id,
                                         @Valid @RequestBody UpdateUserRequest request) {
        User user = updateUserUseCase.execute(id, request);
        return ResponseEntity.ok(Map.of("id", user.getId(), "message", "Usuario actualizado"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> deleteUser(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado"));
    }

    private static Map<String, Object> toSafeMap(User user) {
        return Map.of(
                "id", user.getId(),
                "fullName", user.getFullName(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "active", user.isActive()
        );
    }
}
