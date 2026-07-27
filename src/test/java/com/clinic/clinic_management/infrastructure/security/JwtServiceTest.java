package com.clinic.clinic_management.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET =
            Base64.getEncoder().encodeToString("test-only-secret-key-not-used-in-prod!".getBytes());

    private final JwtService jwtService = new JwtService(SECRET, 60_000);

    @Test
    void generatesATokenThatCarriesTheSubjectAndRoleClaims() {
        String token = jwtService.generateToken("doctor@clinic.com", "ADMIN");

        Claims claims = jwtService.parse(token);

        assertThat(claims.getSubject()).isEqualTo("doctor@clinic.com");
        assertThat(claims.get("role", String.class)).isEqualTo("ADMIN");
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
    }

    @Test
    void rejectsATokenSignedWithADifferentSecret() {
        String foreignSecret = Base64.getEncoder().encodeToString("another-completely-different-secret!".getBytes());
        JwtService otherService = new JwtService(foreignSecret, 60_000);
        String token = otherService.generateToken("doctor@clinic.com", "ADMIN");

        assertThatThrownBy(() -> jwtService.parse(token))
                .isInstanceOf(SignatureException.class);
    }
}
