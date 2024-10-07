/**
 * @author Starling Diaz on 8/10/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 8/10/2024.
 */

package com.mentorly.bankstark.controllers;

import com.mentorly.bankstark.dto.request.AccountRequestDto;
import com.mentorly.bankstark.dto.request.TransactionRequestDto;
import com.mentorly.bankstark.dto.response.TransactionResponseDto;
import com.mentorly.bankstark.service.ITransactionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction", description = "Transaction API")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping("/create")
    @ApiResponse(description = "Create a new transaction")
    @ApiResponse(responseCode = "200", description = "Transaction created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto responseDto = transactionService.createTransaction(requestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/account/list")
    @ApiResponse(description = "Get transactions by account ID")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByAccountId(@RequestBody AccountRequestDto requestDto) {
        List<TransactionResponseDto> transactions = transactionService.getTransactionsByAccountId(requestDto.getAccountId());
        return ResponseEntity.ok(transactions);
    }
}
