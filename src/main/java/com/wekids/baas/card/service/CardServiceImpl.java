package com.wekids.baas.card.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.card.domain.Card;
import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.repository.CardRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final BankMemberRepository bankMemberRepository;

    @Override
    @Transactional
    public CardCreateResponse createCard(CardCreateRequest cardCreateRequest) {
        Account account = getAccount(cardCreateRequest.getAccountNumber());
        BankMember bankMember = getBankMember(cardCreateRequest.getBankMemberId());

        String cardNumber = createCardNumber();
        LocalDate validThru = LocalDate.now().plusYears(5);
        String cvc = createCvc();

        Card card = Card.of(cardNumber, validThru, cvc, bankMember.getName(), cardCreateRequest.getPassword(), account);

        Card savedCard = cardRepository.save(card);

        return CardCreateResponse.of(savedCard.getCardNumber(), savedCard.getValidThru(), savedCard.getCvc(), savedCard.getBankMemberName(), savedCard.getNewDate());
    }

    private BankMember getBankMember(Long bankMemberId) {
        return bankMemberRepository.findById(bankMemberId).orElseThrow(() -> new BaasException(ErrorCode.BANK_MEMBER_NOT_FOUND, "은행 고객 아이디: " + bankMemberId));
    }

    private Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BaasException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌 번호: " + accountNumber));
    }

    private String createCardNumber() {
        StringBuilder cardNumber = new StringBuilder("515954");
        String millis = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        cardNumber.append(millis);

        return cardNumber.toString();
    }

    private String createCvc() {
        long cvc = System.currentTimeMillis() % 1000L;
        return String.valueOf(cvc);
    }
}
