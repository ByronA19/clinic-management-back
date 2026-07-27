package com.clinic.clinic_management.application.user;

import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(String fullName, String email, String password, String role) {
        User user = User.create(fullName, email, passwordEncoder.encode(password), role);
        return userRepositoryPort.save(user);
    }
}
