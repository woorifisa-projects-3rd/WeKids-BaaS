package com.wekids.baas.bankMember.repository;

import com.wekids.baas.bankMember.domain.BankMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankMemberRepository extends JpaRepository<BankMember, Long> {
}
