package com.clinic.clinic_management.application.user;

import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public DeleteUserUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public void execute(Long id) {
        userRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
        userRepositoryPort.deleteById(id);
    }
}
