package com.wekids.baas.registration.repository;

import com.wekids.baas.registration.domain.Registration;
import com.wekids.baas.registration.domain.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {
    Optional<Registration> findByBaasMember_IdAndBankMember_Id(Long baasMemberId, Long bankMemberId);
}
