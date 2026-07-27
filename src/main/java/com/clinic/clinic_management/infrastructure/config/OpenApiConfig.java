package com.clinic.clinic_management.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Clinic Management API",
                version = "1.0.0",
                description = "API REST para la gestión de pacientes, doctores, especialidades y citas de la clínica.",
                contact = @Contact(name = "Clinic Management", email = "admin@clinic.com")
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Autenticación mediante token JWT. Obtén el token en POST /auth/login y colócalo como 'Bearer {token}'."
)
public class OpenApiConfig {
}
