package com.wekids.baas.account.repository;

import com.wekids.baas.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a JOIN FETCH a.bankMember bm JOIN Registration r on bm.id = r.bankMember.id WHERE a.bankMember.id = :bankMemberId AND r.baasMember.id = :baasMemberId")
    List<Account> findAccountsByBankMemberIdAndBaasMemberId(@Param("bankMemberId") Long bankMemberId, @Param("baasMemberId") Long baasMemberId);
}
