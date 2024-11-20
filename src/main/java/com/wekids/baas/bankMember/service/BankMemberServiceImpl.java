package com.wekids.baas.bankMember.service;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.baasMember.repository.BaasMemberRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberCreateResponse;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BankMemberServiceImpl implements BankMemberService{
    private final BankMemberRepository bankMemberRepository;
    private final BaasMemberRepository baasMemberRepository;


    @Override
    @Transactional
    public BankMemberCreateResponse createBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        validateNewBankMember(bankMemberCreateRequest);

        BaasMember baasMember = getBaasMember(bankMemberCreateRequest.getBaasMemberId());

        BankMember bankMember = BankMember.createNewBankMember(bankMemberCreateRequest.getName(), bankMemberCreateRequest.getBirthday(), bankMemberCreateRequest.getResidentRegistrationNumber(), baasMember);

        BankMember savedBankMember = bankMemberRepository.save(bankMember);

        return BankMemberCreateResponse.of(savedBankMember.getId());
    }

    private void validateNewBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        boolean isNewBankMember = bankMemberRepository.findBankMemberByResidentRegistrationNumber(bankMemberCreateRequest.getResidentRegistrationNumber()).isEmpty();

        if(!isNewBankMember) {
            throw new BaasException(ErrorCode.BANK_MEMBER_DUPLICATED, "고객명: " + bankMemberCreateRequest.getName());
        }
    }


    private BaasMember getBaasMember(Long baasMemberId) {
        return baasMemberRepository.findById(baasMemberId).orElseThrow(() -> new BaasException(ErrorCode.BAAS_MEMBER_NOT_FOUND, "BaaS 고객 아이디 : " + baasMemberId));
    }
}
