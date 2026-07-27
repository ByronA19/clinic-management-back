package com.clinic.clinic_management.application.doctor;

import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteDoctorUseCaseTest {

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;

    @Test
    void deletesWhenTheDoctorExists() {
        DeleteDoctorUseCase useCase = new DeleteDoctorUseCase(doctorRepositoryPort);
        Doctor existing = Doctor.create("John", "Doe", "Cardiology", "john.doe@clinic.com");
        existing.setId(1L);
        when(doctorRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));

        useCase.execute(1L);

        verify(doctorRepositoryPort).deleteById(1L);
    }

    @Test
    void throwsWhenTheDoctorDoesNotExist() {
        DeleteDoctorUseCase useCase = new DeleteDoctorUseCase(doctorRepositoryPort);
        when(doctorRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(99L))
                .isInstanceOf(java.util.NoSuchElementException.class);

        verify(doctorRepositoryPort, never()).deleteById(99L);
    }
}
