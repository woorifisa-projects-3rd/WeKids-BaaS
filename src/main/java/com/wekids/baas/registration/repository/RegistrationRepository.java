package com.wekids.baas.registration.repository;

import com.wekids.baas.registration.domain.Registration;
import com.wekids.baas.registration.domain.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {
}
