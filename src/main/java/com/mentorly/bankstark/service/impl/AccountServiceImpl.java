/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */

package com.mentorly.bankstark.service.impl;

import com.mentorly.bankstark.dto.AccountDto;
import com.mentorly.bankstark.dto.PhoneDto;
import com.mentorly.bankstark.dto.UserAccountDetailsDto;
import com.mentorly.bankstark.dto.UserDto;
import com.mentorly.bankstark.dto.request.BalanceUpdateRequest;
import com.mentorly.bankstark.dto.request.BeneficiaryAddRequest;
import com.mentorly.bankstark.dto.response.BeneficiaryResponseDto;
import com.mentorly.bankstark.entities.Account;
import com.mentorly.bankstark.entities.AccountBeneficiary;
import com.mentorly.bankstark.entities.User;
import com.mentorly.bankstark.repository.AccountBeneficiaryRepository;
import com.mentorly.bankstark.repository.AccountRepository;
import com.mentorly.bankstark.repository.UserRepository;
import com.mentorly.bankstark.service.IAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountBeneficiaryRepository accountBeneficiaryRepository;



    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::toAccountDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<UserAccountDetailsDto> getUserAndAccounts(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if(user == null) {
            return Optional.empty();
        }

        UserAccountDetailsDto detailsDto = UserAccountDetailsDto.builder()
                .user(toUserDto(user))
                .accounts(accountRepository.findByUserId(userId).stream()
                        .map(this::toAccountDto)
                        .collect(Collectors.toList()))
                .build();
        return Optional.of(detailsDto);
    }


    @Override
    public AccountDto addBalance(BalanceUpdateRequest balanceUpdateRequest) {
        Account account = accountRepository.findById(balanceUpdateRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setBalance(account.getBalance() + balanceUpdateRequest.getAmount());
        account = accountRepository.save(account);
        return toAccountDto(account);
    }

    @Override
    public String addBeneficiary(BeneficiaryAddRequest request) {
        // Find the beneficiary user by their email
        User beneficiary = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found"));

        // Retrieve the main account to which the beneficiary will be added
        Account mainAccount = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("main Account not found"));

        //Use the accountOwnerId from the request
        String accountOwnerId = request.getAccountOwnerId();

        // Create the account beneficiary
        AccountBeneficiary newBeneficiary = AccountBeneficiary.builder()
                .account(mainAccount)
                .beneficiaryUserId(beneficiary.getId())
                .beneficiaryEmail(beneficiary.getEmail())
                .accountOwnerId(accountOwnerId)
                .beneficiaryId(beneficiary.getId())
                .addedAt(LocalDateTime.now())
                .build();

        accountBeneficiaryRepository.save(newBeneficiary);

        return newBeneficiary.getId();
    }

    @Override
    public List<BeneficiaryResponseDto> getBeneficiariesByAccountOwnerId(String accountOwnerId) {
        List<AccountBeneficiary> beneficiaries = accountBeneficiaryRepository.findByAccountOwnerId(accountOwnerId);
        return beneficiaries.stream()
                .map(beneficiary -> BeneficiaryResponseDto.builder()
                        .id(beneficiary.getId())
                        .beneficiaryId(beneficiary.getBeneficiaryId())
                        .beneficiaryEmail(beneficiary.getBeneficiaryEmail())
                        .accountId(beneficiary.getAccount().getId())
                        .accountOwnerId(beneficiary.getAccountOwnerId())
                        .beneficiaryUserId(beneficiary.getBeneficiaryUserId())
                        .addedAt(beneficiary.getAddedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }


    private AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .accountType(account.getAccountType())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .balance(account.getBalance())
                .build();
    }

    private UserDto toUserDto(User user) {
        Set<PhoneDto> phoneDtos = user.getPhones().stream()
                .map(phone -> PhoneDto.builder()
                        .number(phone.getNumber())
                        .citycode(phone.getCitycode())
                        .countrycode(phone.getCountrycode())
                        .build())
                .collect(Collectors.toSet());
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .dniNumber(user.getDniNumber())
                .passportNumber(user.getPassportNumber())
                .email(user.getEmail())
                .phones(phoneDtos)
                .build();
    }

}
