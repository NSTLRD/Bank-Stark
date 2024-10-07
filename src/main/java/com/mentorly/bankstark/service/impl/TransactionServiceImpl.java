/**
 * @author Starling Diaz on 8/7/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 8/7/2024.
 */

package com.mentorly.bankstark.service.impl;

import com.mentorly.bankstark.dto.request.TransactionRequestDto;
import com.mentorly.bankstark.dto.response.TransactionResponseDto;
import com.mentorly.bankstark.entities.Account;
import com.mentorly.bankstark.entities.Transaction;
import com.mentorly.bankstark.repository.AccountRepository;
import com.mentorly.bankstark.repository.TransactionRepository;
import com.mentorly.bankstark.service.ITransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {
        Optional<Account> fromAccountOptional = accountRepository.findById(String.valueOf(UUID.fromString(requestDto.getFromAccountId())));
        Optional<Account> toAccountOptional = accountRepository.findById(String.valueOf(UUID.fromString(requestDto.getToAccountId())));

        if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) {
            throw new IllegalStateException("One or both accounts not found.");
        }
        Account fromAccount = fromAccountOptional.get();
        Account toAccount = toAccountOptional.get();

        // Check for sufficient funds in fromAccount
        if (fromAccount.getBalance() < requestDto.getAmount()) {
            throw new IllegalStateException("Insufficient funds.");
        }
        // Create transaction
        Transaction transaction = Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(requestDto.getAmount())
                .status("IN PROGRESS")
                .transferType(requestDto.getTransferType())
                .createdAt(LocalDateTime.now())
                .build();
        // Save transaction
        transactionRepository.save(transaction);

        //Prepare response
        TransactionResponseDto responseDto = TransactionResponseDto.builder()
                .transactionId(transaction.getTransactionId().toString())
                .fromAccountId(transaction.getFromAccount().getId())
                .toAccountId(transaction.getToAccount().getId())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .transferType(transaction.getTransferType())
                .createdAt(transaction.getCreatedAt())
                .build();

        return responseDto;

    }

    @Override
    public List<TransactionResponseDto> getTransactionsByAccountId(String accountId) {
        UUID accountUuid = UUID.fromString(accountId);
        List<Transaction> transactions = transactionRepository.findByFromAccountId(String.valueOf(accountUuid));
        return transactions.stream()
                .map(this::toTransactionResponseDto)
                .collect(Collectors.toList());
    }

    private TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .transactionId(transaction.getTransactionId().toString())
                .fromAccountId(transaction.getFromAccount().getId())
                .toAccountId(transaction.getToAccount().getId())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .transferType(transaction.getTransferType())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
