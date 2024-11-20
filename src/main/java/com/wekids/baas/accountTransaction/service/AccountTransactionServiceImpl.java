package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService{
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transfer(TransferRequest transferRequest) {
        Account senderAccount = getAccount(transferRequest.getSenderAccountNumber());
        Account receiverAccount = getAccount(transferRequest.getReceiverAccountNumber());
        BigDecimal amount = transferRequest.getAmount();

        validateTransfer(senderAccount, receiverAccount, amount);

        BankMember sender = senderAccount.getBankMember();
        BankMember receiver = receiverAccount.getBankMember();

        LocalDateTime now = LocalDateTime.now();

        senderAccount.withdraw(amount);
        AccountTransaction senderTransaction = AccountTransaction.createNewAccountTransaction(receiver.getName(), AccountTransactionType.WITHDRAW, amount.negate(), senderAccount.getBalance(), sender.getName(), receiver.getName(), now, CurrencyCode.KRW, senderAccount);

        receiverAccount.deposit(amount);
        AccountTransaction receiverTransaction = AccountTransaction.createNewAccountTransaction(sender.getName(), AccountTransactionType.DEPOSIT, amount, receiverAccount.getBalance(), sender.getName(), receiver.getName(), now, CurrencyCode.KRW, receiverAccount);

        accountTransactionRepository.save(senderTransaction);
        accountTransactionRepository.save(receiverTransaction);
    }

    private static void validateTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount) {
        if(senderAccount.getBalance().compareTo(amount) < 0) throw new BaasException(ErrorCode.INSUFFICIENT_BALANCE, "잔액: " + senderAccount.getBalance());
        if(senderAccount.getAccountNumber() == receiverAccount.getAccountNumber()) throw new BaasException(ErrorCode.SENDER_AND_RECEIVER_SAME, "보내는 분 계좌번호: " + senderAccount.getAccountNumber() + " 받는 분 계좌번호: " + receiverAccount.getAccountNumber());
    }

    private Account getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BaasException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌 번호: " + accountNumber));

        if(account.getState() == AccountState.INACTIVE) throw new BaasException(ErrorCode.ACCOUNT_INACTIVE, "계좌 번호: " + accountNumber);

        return account;
    }
}
