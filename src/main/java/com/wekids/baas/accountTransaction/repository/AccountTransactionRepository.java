package com.wekids.baas.accountTransaction.repository;

import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
    @Query("select t from AccountTransaction t " +
            "where t.account.accountNumber = :accountNumber " +
            "and t.transactionDate between :start and :end " +
            "and (:type = 'ALL' or t.type = :type)")
    List<AccountTransaction> findAccountTransactionsByCondition(@Param("accountNumber") String accountNumber,
                                                                @Param("start") LocalDateTime start,
                                                                @Param("end") LocalDateTime end,
                                                                @Param("type") AccountTransactionType type,
                                                                Pageable pageable);
}
