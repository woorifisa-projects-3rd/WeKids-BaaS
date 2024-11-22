package com.wekids.baas.account.dto.response;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.product.domain.Product;
import com.wekids.baas.product.domain.enums.ProductType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MemberAccountGetResponse {
    private String accountNumber;
    private String bankName;
    private BigDecimal balance;
    private AccountState state;
    private String bankMemberName;
    private String productName;
    private ProductType productType;

    public static MemberAccountGetResponse from(Account account) {
        BankMember bankMember = account.getBankMember();
        Product product = account.getProduct();

        return MemberAccountGetResponse.builder()
                .accountNumber(account.getAccountNumber())
                .bankName(account.getBankCode().getName())
                .balance(account.getBalance())
                .state(account.getState())
                .bankMemberName(bankMember.getName())
                .productName(product.getName())
                .productType(product.getType())
                .build();
    }

    public static List<MemberAccountGetResponse> from(List<Account> accounts) {
        return accounts.stream()
                .map(account -> from(account))
                .collect(Collectors.toList());
    }
}
