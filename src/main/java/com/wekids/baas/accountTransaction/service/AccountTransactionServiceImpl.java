package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import com.wekids.baas.accountTransaction.dto.request.AccountTransactionRequestType;
import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionResponse;
import com.wekids.baas.accountTransaction.dto.response.TransferResponse;
import com.wekids.baas.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService{
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        Account senderAccount = getAccount(transferRequest.getSenderAccountNumber());
        Account receiverAccount = getAccount(transferRequest.getReceiverAccountNumber());
        BigDecimal amount = transferRequest.getAmount();

        validateTransfer(senderAccount, receiverAccount, amount);

        BankMember sender = senderAccount.getBankMember();
        BankMember receiver = receiverAccount.getBankMember();

        LocalDateTime now = LocalDateTime.now();

        AccountTransaction withdraw = withdraw(senderAccount, amount, sender, receiver, now);

        AccountTransaction deposit = deposit(receiverAccount, amount, sender, receiver, now);

        return TransferResponse.of(TransactionResponse.from(withdraw), TransactionResponse.from(deposit));
    }

    @Override
    public List<TransactionResponse> getTransactionList(TransactionGetRequest transactionGetRequest) {
        String accountNumber = transactionGetRequest.getAccountNumber();

        validateAccountNumber(accountNumber);

        LocalDateTime start = transactionGetRequest.getStart();
        LocalDateTime end = transactionGetRequest.getEnd();

        validateDateCondition(start, end);

        AccountTransactionType type = getType(transactionGetRequest.getType());

        PageRequest pageRequest = PageRequest.of(transactionGetRequest.getPage(), transactionGetRequest.getSize());

        List<AccountTransaction> accountTransactions = accountTransactionRepository.findAccountTransactionsByCondition(accountNumber, start, end, type, pageRequest);

        return TransactionResponse.from(accountTransactions);
    }

    private void validateDateCondition(LocalDateTime start, LocalDateTime end) {
        if(start.isAfter(end)) throw new BaasException(ErrorCode.START_IS_AFTER_END, "시작날짜: " + start + " 끝날짜: " + end);
    }

    private void validateAccountNumber(String accountNumber) {
        Account account = getAccount(accountNumber);

        if(account.getState() == AccountState.INACTIVE) throw new BaasException(ErrorCode.INACTIVE_ACCOUNT, "계좌번호: " + accountNumber);
    }

    private AccountTransactionType getType(AccountTransactionRequestType accountTransactionRequestType) {
        return accountTransactionRequestType.equals(AccountTransactionRequestType.ALL) ? null : AccountTransactionType.valueOf(accountTransactionRequestType.name());
    }

    private void validateTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount) {
        if (senderAccount.getBalance().compareTo(amount) < 0)
            throw new BaasException(ErrorCode.INSUFFICIENT_BALANCE, "잔액: " + senderAccount.getBalance());
        if (senderAccount.getAccountNumber() == receiverAccount.getAccountNumber())
            throw new BaasException(ErrorCode.SENDER_AND_RECEIVER_SAME, "보내는 분 계좌번호: " + senderAccount.getAccountNumber() + " 받는 분 계좌번호: " + receiverAccount.getAccountNumber());
    }

    private Account getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BaasException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌 번호: " + accountNumber));

        if (account.getState() == AccountState.INACTIVE)
            throw new BaasException(ErrorCode.INACTIVE_ACCOUNT, "계좌 번호: " + accountNumber);

        return account;
    }

    private AccountTransaction deposit(Account receiverAccount, BigDecimal amount, BankMember sender, BankMember receiver, LocalDateTime now) {
        receiverAccount.deposit(amount);
        AccountTransaction receiverTransaction = AccountTransaction.createNewAccountTransaction(sender.getName(), AccountTransactionType.DEPOSIT, amount, receiverAccount.getBalance(), sender.getName(), receiver.getName(), now, CurrencyCode.KRW, receiverAccount);
        return accountTransactionRepository.save(receiverTransaction);
    }

    private AccountTransaction withdraw(Account senderAccount, BigDecimal amount, BankMember sender, BankMember receiver, LocalDateTime now) {
        senderAccount.withdraw(amount);
        AccountTransaction senderTransaction = AccountTransaction.createNewAccountTransaction(receiver.getName(), AccountTransactionType.WITHDRAW, amount.negate(), senderAccount.getBalance(), sender.getName(), receiver.getName(), now, CurrencyCode.KRW, senderAccount);
        return accountTransactionRepository.save(senderTransaction);
    }
}
