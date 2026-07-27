package com.clinic.clinic_management.domain.specialty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Specialty create(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        LocalDateTime now = LocalDateTime.now();
        return new Specialty(null, name.trim(), description, true, now, now);
    }
}
