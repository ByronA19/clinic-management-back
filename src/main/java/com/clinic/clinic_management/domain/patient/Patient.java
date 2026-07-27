package com.clinic.clinic_management.domain.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private Long id;
    private String firstName;
    private String lastName;
    private String documento;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Patient create(String firstName, String lastName, String documento, String phone, String email) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is required");
        }
        LocalDateTime now = LocalDateTime.now();
        return new Patient(null, firstName.trim(), lastName.trim(), documento, phone, email, now, now);
    }
}
