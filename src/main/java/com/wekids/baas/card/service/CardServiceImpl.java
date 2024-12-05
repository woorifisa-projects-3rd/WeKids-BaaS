package com.wekids.baas.card.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.card.domain.Card;
import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.request.CardStateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.dto.response.CardStateResponse;
import com.wekids.baas.card.repository.CardRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.registration.repository.RegistrationRepository;
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
    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public CardCreateResponse createCard(CardCreateRequest cardCreateRequest) {
        Account account = getAccount(cardCreateRequest.getAccountNumber());
        BankMember bankMember = getBankMember(cardCreateRequest.getBankMemberId());

        String cardNumber = createCardNumber();
        LocalDate validThru = LocalDate.now().plusYears(5);
        String cvc = createCvc();

        Card card = Card.createNewCard(cardNumber, validThru, cvc, bankMember.getName(), cardCreateRequest.getPassword(), account);

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

        cardNumber = cardNumber.insert(4, '-').insert(9, '-').insert(14, '-');

        return cardNumber.toString();
    }

    private String createCvc() {
        return String.valueOf(System.currentTimeMillis()).substring(10, 13);
    }

    @Override
    @Transactional
    public CardStateResponse changeCardState(CardStateRequest request) {
        validateRegistration(request);

        Card card = getCard(request.getCardNumber(), request.getCvc());

        card.updateCardState(request.getState());

        return CardStateResponse.from(card.getInactiveDate());
    }

    private void validateRegistration(CardStateRequest request){
        registrationRepository.findByBaasMember_IdAndBankMember_Id(request.getBaasMemberId(), request.getBankMemberId())
                .orElseThrow(()->new BaasException(ErrorCode.REGISTRATION_NOT_FOUND, String.format("%d과 %d은 등록되지 않았습니다.", request.getBaasMemberId() , request.getBankMemberId())));
    }

    private Card getCard(String cardNumber, String cvc){
        Card card = cardRepository.findByCardNumberAndCvc(cardNumber, cvc).orElseThrow(() ->
                new BaasException(ErrorCode.CARD_NOT_FOUNT, cardNumber + ", " + cvc + "은 존재하지 않습니다."));

        if(card.getValidThru().isBefore(LocalDate.now())){
            throw new BaasException(ErrorCode.CARD_EXPIRE,  cardNumber + ", " + cvc + "은 말료된 카드입니다");
        }

        return card;
    }
}
