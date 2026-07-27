package com.clinic.clinic_management.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    void deleteById(Long id);
}
