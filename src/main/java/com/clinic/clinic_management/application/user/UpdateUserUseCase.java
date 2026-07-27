package com.clinic.clinic_management.application.user;

import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import com.clinic.clinic_management.infrastructure.web.dto.UpdateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(Long id, UpdateUserRequest request) {
        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado: " + id));

        if (request.fullName() != null) {
            user.setFullName(request.fullName());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }
        if (request.password() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        if (request.role() != null) {
            user.setRole(request.role());
        }
        if (request.active() != null) {
            user.setActive(request.active());
        }
        user.setUpdatedAt(LocalDateTime.now());

        return userRepositoryPort.save(user);
    }
}
