package com.wekids.baas.support.fixture;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.domain.enums.BankCode;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.product.domain.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class AccountFixture {
    private Long id;
    @Builder.Default
    private String accountNumber = "1002-123-456789";
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
    @Builder.Default
    private String password = "1234";
    @Builder.Default
    private LocalDateTime expireDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
    private LocalDateTime inactiveDate;
    @Builder.Default
    private AccountState state = AccountState.ACTIVE;
    @Builder.Default
    private Product product = ProductFixture.builder().build().product();
    @Builder.Default
    private BankMember bankMember = BankMemberFixture.builder().build().bankMember();
    @Builder.Default
    private BankCode bankCode = BankCode.WOORI_BANK;

    public Account account() {
        return Account.builder()
                .id(id)
                .accountNumber(accountNumber)
                .balance(balance)
                .password(password)
                .expireDate(expireDate)
                .inactiveDate(inactiveDate)
                .state(state)
                .product(product)
                .bankMember(bankMember)
                .bankCode(bankCode)
                .build();
    }
}
