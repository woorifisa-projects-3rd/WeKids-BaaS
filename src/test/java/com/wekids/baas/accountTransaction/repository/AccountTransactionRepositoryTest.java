package com.wekids.baas.accountTransaction.repository;

import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @ParameterizedTest
    @CsvSource({"ALL, 4", "DEPOSIT, 0", "WITHDRAW, 1"})
    void findAccountTransactionByCondition(String type, int answer) {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2024, 11, 1), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2024, 11, 30), LocalTime.MAX);
        AccountTransactionType txType = type.equals("ALL") ? null : AccountTransactionType.valueOf(type);

        PageRequest pageRequest = PageRequest.of(1, 5);

        List<AccountTransaction> transactions = accountTransactionRepository.findAccountTransactionsByCondition("1002-913-023908", start, end, txType, pageRequest);

        System.out.println("transactions = " + transactions);

        Assertions.assertThat(transactions.size()).isEqualTo(answer);
    }

    @ParameterizedTest
    @CsvSource({"2024, 11, 20, 2024, 11, 21, 3", "2024, 11, 20, 2024, 11, 24, 5"})
    void findAccountTransactionByCondition_date_condition(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int answer) {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(startYear, startMonth, startDay), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(endYear, endMonth, endDay), LocalTime.MAX);
        AccountTransactionType txType = null;

        PageRequest pageRequest = PageRequest.of(0, 5);

        List<AccountTransaction> transactions = accountTransactionRepository.findAccountTransactionsByCondition("1002-913-023908", start, end, txType, pageRequest);

        System.out.println("transactions = " + transactions);

        Assertions.assertThat(transactions.size()).isEqualTo(answer);
    }
}