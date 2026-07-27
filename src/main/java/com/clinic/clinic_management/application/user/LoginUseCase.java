package com.clinic.clinic_management.application.user;

import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import com.clinic.clinic_management.infrastructure.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUseCase(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String execute(String email, String password) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!user.isActive() || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return jwtService.generateToken(user.getEmail(), user.getRole());
    }
}
