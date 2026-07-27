package com.clinic.clinic_management.application.user;

import com.clinic.clinic_management.domain.user.User;
import com.clinic.clinic_management.domain.user.UserRepositoryPort;
import com.clinic.clinic_management.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private User activeUser() {
        User user = User.create("Admin", "admin@clinic.com", "hashed-password", "ADMIN");
        user.setId(1L);
        return user;
    }

    @Test
    void returnsATokenForValidCredentials() {
        LoginUseCase useCase = new LoginUseCase(userRepositoryPort, passwordEncoder, jwtService);
        User user = activeUser();
        when(userRepositoryPort.findByEmail("admin@clinic.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Admin123!", "hashed-password")).thenReturn(true);
        when(jwtService.generateToken("admin@clinic.com", "ADMIN")).thenReturn("fake-jwt-token");

        String token = useCase.execute("admin@clinic.com", "Admin123!");

        assertThat(token).isEqualTo("fake-jwt-token");
    }

    @Test
    void rejectsAnUnknownEmail() {
        LoginUseCase useCase = new LoginUseCase(userRepositoryPort, passwordEncoder, jwtService);
        when(userRepositoryPort.findByEmail("ghost@clinic.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("ghost@clinic.com", "whatever"))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void rejectsAWrongPassword() {
        LoginUseCase useCase = new LoginUseCase(userRepositoryPort, passwordEncoder, jwtService);
        User user = activeUser();
        when(userRepositoryPort.findByEmail("admin@clinic.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "hashed-password")).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute("admin@clinic.com", "wrong-password"))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void rejectsAnInactiveUserEvenWithTheRightPassword() {
        LoginUseCase useCase = new LoginUseCase(userRepositoryPort, passwordEncoder, jwtService);
        User user = activeUser();
        user.setActive(false);
        when(userRepositoryPort.findByEmail("admin@clinic.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> useCase.execute("admin@clinic.com", "Admin123!"))
                .isInstanceOf(BadCredentialsException.class);
    }
}
