package com.clinic.clinic_management.application.doctor;

import com.clinic.clinic_management.domain.doctor.Doctor;
import com.clinic.clinic_management.domain.doctor.DoctorRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDoctorUseCaseTest {

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;

    @Test
    void savesANewDoctorBuiltFromTheGivenData() {
        CreateDoctorUseCase useCase = new CreateDoctorUseCase(doctorRepositoryPort);
        when(doctorRepositoryPort.save(any(Doctor.class)))
                .thenAnswer(invocation -> {
                    Doctor toSave = invocation.getArgument(0);
                    toSave.setId(1L);
                    return toSave;
                });

        Doctor result = useCase.execute("John", "Doe", "Cardiology", "john.doe@clinic.com");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.isActive()).isTrue();

        ArgumentCaptor<Doctor> captor = ArgumentCaptor.forClass(Doctor.class);
        verify(doctorRepositoryPort).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("john.doe@clinic.com");
    }

    @Test
    void propagatesValidationErrorsFromTheDomainWithoutTouchingTheRepository() {
        CreateDoctorUseCase useCase = new CreateDoctorUseCase(doctorRepositoryPort);

        assertThatThrownBy(() -> useCase.execute("", "Doe", "Cardiology", "john.doe@clinic.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nombre requerido");

        verifyNoInteractions(doctorRepositoryPort);
    }
}
