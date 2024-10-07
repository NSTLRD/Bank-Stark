/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */
package com.mentorly.bankstark.service;

import com.mentorly.bankstark.dto.AccountDto;
import com.mentorly.bankstark.dto.UserAccountDetailsDto;
import com.mentorly.bankstark.dto.request.BalanceUpdateRequest;
import com.mentorly.bankstark.dto.request.BeneficiaryAddRequest;
import com.mentorly.bankstark.dto.response.BeneficiaryResponseDto;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<AccountDto> getAllAccounts();

    Optional<UserAccountDetailsDto> getUserAndAccounts(String userId);

    AccountDto addBalance(BalanceUpdateRequest balanceUpdateRequest);

    String addBeneficiary(BeneficiaryAddRequest request);

    List<BeneficiaryResponseDto> getBeneficiariesByAccountOwnerId(String accountOwnerId);

}
