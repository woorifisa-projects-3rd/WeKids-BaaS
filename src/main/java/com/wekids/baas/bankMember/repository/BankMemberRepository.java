package com.wekids.baas.bankMember.repository;

import com.wekids.baas.bankMember.domain.BankMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankMemberRepository extends JpaRepository<BankMember, Long> {
    Optional<BankMember> findBankMemberByResidentRegistrationNumber(String residentRegistrationNumber);
}
