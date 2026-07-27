package com.clinic.clinic_management.domain.doctor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DoctorTest {

    @Test
    void createsActiveDoctorWithTrimmedNames() {
        Doctor doctor = Doctor.create(" John ", " Doe ", "Cardiology", "john.doe@clinic.com");

        assertThat(doctor.getFirstName()).isEqualTo("John");
        assertThat(doctor.getLastName()).isEqualTo("Doe");
        assertThat(doctor.getSpecialty()).isEqualTo("Cardiology");
        assertThat(doctor.getEmail()).isEqualTo("john.doe@clinic.com");
        assertThat(doctor.isActive()).isTrue();
        assertThat(doctor.getId()).isNull();
        assertThat(doctor.getCreatedAt()).isNotNull();
        assertThat(doctor.getUpdatedAt()).isEqualTo(doctor.getCreatedAt());
    }

    @Test
    void rejectsBlankFirstName() {
        assertThatThrownBy(() -> Doctor.create("  ", "Doe", "Cardiology", "john.doe@clinic.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nombre requerido");
    }

    @Test
    void rejectsBlankLastName() {
        assertThatThrownBy(() -> Doctor.create("John", null, "Cardiology", "john.doe@clinic.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Apellido requerido");
    }
}
