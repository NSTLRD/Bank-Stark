/**
 * @author Starling Diaz on 8/10/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 8/10/2024.
 */

package com.mentorly.bankstark.controllers;

import com.mentorly.bankstark.dto.AccountDto;
import com.mentorly.bankstark.dto.UserAccountDetailsDto;
import com.mentorly.bankstark.dto.request.BalanceUpdateRequest;
import com.mentorly.bankstark.dto.request.BeneficiaryAddRequest;
import com.mentorly.bankstark.dto.response.BeneficiaryResponseDto;
import com.mentorly.bankstark.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account Controller", description = "Account Controller for managing accounts.")
public class AccountController {

    private final IAccountService accountService;


    @GetMapping("/all")
    @Operation(summary = "Get all accounts")
    @ApiResponse(responseCode = "200", description = "Return all accounts")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/details/{userId}")
    @Operation(summary = "Get user and accounts details")
    @ApiResponse(responseCode = "200", description = "Return user and accounts details")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserAccountDetailsDto> getUserAndAccounts(@PathVariable String userId) {
        return accountService.getUserAndAccounts(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/balance")
    @Operation(summary = "Add balance to account")
    @ApiResponse(responseCode = "200", description = "Return updated account")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<AccountDto> addBalance(@RequestBody BalanceUpdateRequest request) {
        AccountDto updatedAccount = accountService.addBalance(request);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/add-beneficiary")
    @Operation(summary = "Add beneficiary to account")
    @ApiResponse(responseCode = "200", description = "Return account id")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> addBeneficiary(@RequestBody BeneficiaryAddRequest request) {
        String accountId = accountService.addBeneficiary(request);
        return ResponseEntity.ok(accountId);
    }

    @GetMapping("/beneficiaries/{accountId}")
    @Operation(summary = "Get all beneficiaries for an account")
    @ApiResponse(responseCode = "200", description = "Return all beneficiaries")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<BeneficiaryResponseDto>> getBeneficiariesByAccountId(@PathVariable String accountId) {
        List<BeneficiaryResponseDto> beneficiaries = accountService.getBeneficiariesByAccountOwnerId(accountId);
        if (beneficiaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(beneficiaries);
    }



}
