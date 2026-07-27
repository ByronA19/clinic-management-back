package com.clinic.clinic_management.infrastructure.security;

import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrapRunner implements ApplicationRunner {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminBootstrapRunner(UserRepositoryPort userRepositoryPort,
                                 PasswordEncoder passwordEncoder,
                                 @Value("${app.bootstrap-admin.email}") String adminEmail,
                                 @Value("${app.bootstrap-admin.password}") String adminPassword) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepositoryPort.findAll().isEmpty()) {
            return;
        }
        User admin = User.create("Administrador", adminEmail, passwordEncoder.encode(adminPassword), "ADMIN");
        userRepositoryPort.save(admin);
        System.out.println("Admin inicial creado -> email: " + adminEmail);
    }
}
