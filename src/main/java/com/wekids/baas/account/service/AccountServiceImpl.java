package com.wekids.baas.account.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.request.MemberAccountGetRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;
import com.wekids.baas.account.dto.response.MemberAccountGetResponse;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.domain.enums.BankMemberState;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.product.domain.Product;
import com.wekids.baas.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BankMemberRepository bankMemberRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest) {
        BankMember bankMember = getBankMember(accountCreateRequest.getBankMemberId());
        Product product = getProduct(accountCreateRequest.getProductId());

        String accountNumber = createAccountNumber();
        String password = accountCreateRequest.getPassword();
        LocalDateTime expireDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

        Account account = Account.createNewAccount(accountNumber, password, expireDate, product, bankMember);

        Account newAccount = accountRepository.save(account);

        return AccountCreateResponse.of(newAccount.getAccountNumber(), product.getType().name(), newAccount.getExpireDate());
    }

    @Override
    public List<MemberAccountGetResponse> getMemberAccountList(Long baasMemberId, Long bankMemberId) {
        List<Account> memberAccounts = accountRepository.findAccountsByBankMemberIdAndBaasMemberId(bankMemberId, baasMemberId);

        return MemberAccountGetResponse.from(memberAccounts);
    }

    @Override
    public MemberAccountGetResponse getMemberAccount(MemberAccountGetRequest memberAccountGetRequest) {
        Account account = getAccount(memberAccountGetRequest.getAccountNumber());
        return MemberAccountGetResponse.from(account);
    }

    private Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BaasException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌 번호: " + accountNumber));
    }

    private String createAccountNumber() {
        StringBuilder accountNumber = new StringBuilder("1002");

        String millis = String.valueOf(System.currentTimeMillis()).substring(4, 13);
        accountNumber.append(millis);

        accountNumber.insert(4, '-').insert(8, '-');

        return accountNumber.toString();
    }

    private Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BaasException(ErrorCode.PRODUCT_NOT_FOUND, "계좌 상품 아이디: " + productId));

        LocalDateTime now = LocalDateTime.now();

        if(product.getStartDate().isAfter(now) || product.getEndDate().isBefore(now) && product.getEndDate().isEqual(now)) throw new BaasException(ErrorCode.INVALID_PRODUCT, "계좌 상품 아이디: " + productId);

        return product;
    }

    private BankMember getBankMember(Long bankMemberId) {
        BankMember bankMember = bankMemberRepository.findById(bankMemberId).orElseThrow(() -> new BaasException(ErrorCode.BANK_MEMBER_NOT_FOUND, "은행 고객 아이디: " + bankMemberId));

        if(bankMember.getState() == BankMemberState.INACTIVE) throw new BaasException(ErrorCode.INACTIVE_BANK_MEMBER, "은행 고객 아이디: " + bankMemberId);

        return bankMember;
    }
}
