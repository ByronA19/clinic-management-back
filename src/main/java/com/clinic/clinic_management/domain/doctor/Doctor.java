package com.clinic.clinic_management.domain.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String email;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Doctor create(String firstName, String lastName, String specialty, String email) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Apellido requerido");
        }
        LocalDateTime now = LocalDateTime.now();
        return new Doctor(null, firstName.trim(), lastName.trim(), specialty, email, true, now, now);
    }
}
