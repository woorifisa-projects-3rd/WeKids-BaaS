package com.wekids.baas.registration.service;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.baasMember.repository.BaasMemberRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.registration.domain.RegistrationId;
import com.wekids.baas.registration.repository.RegistrationRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.registration.domain.Registration;
import com.wekids.baas.registration.dto.request.RegisterRequest;
import com.wekids.baas.registration.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final BankMemberRepository bankMemberRepository;
    private final BaasMemberRepository baasMemberRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        BaasMember baasMember = getBaasMember(registerRequest.getBaasMemberId());

        BankMember bankMember = getOrCreateBankMember(registerRequest);

        Long bankMemberId = register(baasMember, bankMember);

        return RegisterResponse.of(bankMemberId);
    }

    private Long register(BaasMember baasMember, BankMember bankMember) {
        validatedRegistration(baasMember, bankMember);

        Registration registration = Registration.register(baasMember, bankMember);
        Registration newRegistration = registrationRepository.save(registration);

        return newRegistration.getBankMember().getId();
    }

    private void validatedRegistration(BaasMember baasMember, BankMember bankMember) {
        RegistrationId registrationId = RegistrationId.of(baasMember.getId(), bankMember.getId());

        if(registrationRepository.findById(registrationId).isPresent()) {
            throw new BaasException(ErrorCode.REGISTRATION_DUPLICATED, "BaaS 고객 아이디: " + baasMember.getId() + ", 은행 고객 아이디: " + bankMember.getId());
        }
    }

    private BankMember getOrCreateBankMember(RegisterRequest registerRequest) {
        BankMember bankMember = getBankMemberByResidentRegistrationNumber(registerRequest);

        if(bankMember != null) return bankMember;

        BankMember newBankMember = BankMember.createNewBankMember(registerRequest.getName(), registerRequest.getBirthday(), registerRequest.getResidentRegistrationNumber());
        return bankMemberRepository.save(newBankMember);
    }

    private BankMember getBankMemberByResidentRegistrationNumber(RegisterRequest registerRequest) {
        return bankMemberRepository.findBankMemberByResidentRegistrationNumber(registerRequest.getResidentRegistrationNumber()).orElse(null);
    }


    private BaasMember getBaasMember(Long baasMemberId) {
        return baasMemberRepository.findById(baasMemberId).orElseThrow(() -> new BaasException(ErrorCode.BAAS_MEMBER_NOT_FOUND, "BaaS 고객 아이디 : " + baasMemberId));
    }
}
