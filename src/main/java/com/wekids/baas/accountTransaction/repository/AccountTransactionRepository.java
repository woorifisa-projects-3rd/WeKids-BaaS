package com.wekids.baas.accountTransaction.repository;

import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
