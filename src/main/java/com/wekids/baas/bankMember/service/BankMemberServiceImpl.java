package com.wekids.baas.bankMember.service;

import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.domain.enums.BankMemberState;
import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.request.BankMemberIdGetRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberIdResponse;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BankMemberServiceImpl implements BankMemberService {
    private final BankMemberRepository bankMemberRepository;

    @Override
    @Transactional
    public BankMemberIdResponse createBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        validateNewBankMember(bankMemberCreateRequest);

        BankMember bankMember = BankMember.createNewBankMember(bankMemberCreateRequest.getName(), bankMemberCreateRequest.getBirthday(), bankMemberCreateRequest.getResidentRegistrationNumber());

        BankMember savedBankMember = bankMemberRepository.save(bankMember);

        return BankMemberIdResponse.of(savedBankMember.getId());
    }

    @Override
    public BankMemberIdResponse getBankMemberId(BankMemberIdGetRequest bankMemberIdGetRequest) {
        BankMember bankMember = getBankMember(bankMemberIdGetRequest.getResidentRegistrationNumber());

        validateBankMember(bankMember);

        return BankMemberIdResponse.of(bankMember.getId());
    }

    private void validateBankMember(BankMember bankMember) {
        if(bankMember.getState().equals(BankMemberState.INACTIVE)) throw new BaasException(ErrorCode.INACTIVE_BANK_MEMBER, "은행 고객 아이디: " + bankMember.getId());
    }

    private BankMember getBankMember(String residentRegistrationNumber) {
        String maskingResidentRegistrationNumber = new StringBuilder(residentRegistrationNumber)
                .replace(7, 13, "*")
                .toString();
        
        return bankMemberRepository.findBankMemberByResidentRegistrationNumber(residentRegistrationNumber).orElseThrow(() -> new BaasException(ErrorCode.BANK_MEMBER_NOT_FOUND, "주민등록번호: " + maskingResidentRegistrationNumber));
    }

    private void validateNewBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        Optional<BankMember> bankMember = bankMemberRepository.findBankMemberByResidentRegistrationNumber(bankMemberCreateRequest.getResidentRegistrationNumber());

        boolean isNewBankMember = bankMember.isEmpty();

        if (!isNewBankMember) {
            throw new BaasException(ErrorCode.BANK_MEMBER_DUPLICATED, "생성 요청된 고객명: " + bankMemberCreateRequest.getName() + ", 조회된 고객명: " + bankMember.get().getName());
        }
    }
}
