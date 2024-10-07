/**
 * @author Starling Diaz on 8/7/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 8/7/2024.
 */

package com.mentorly.bankstark.job;

import com.mentorly.bankstark.entities.Account;
import com.mentorly.bankstark.entities.Transaction;
import com.mentorly.bankstark.repository.AccountRepository;
import com.mentorly.bankstark.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TransactionProcessingService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionProcessingService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedDelay = 45000)  // Run every 45 seconds
    public void processTransactions() {
        log.info("Starting transaction processing job");
        List<Transaction> transactions = transactionRepository.findByStatus("IN PROGRESS");
        log.info("Found {} transactions in progress", transactions.size());

        for (Transaction transaction : transactions) {
            synchronized (this) {
                transaction = transactionRepository.findById(transaction.getTransactionId()).orElse(null);
                if (transaction == null || !transaction.getStatus().equals("IN PROGRESS")) {
                    continue;
                }

                Account fromAccount = transaction.getFromAccount();
                Account toAccount = transaction.getToAccount();
                log.info("Processing transaction from account: {} to account: {}", fromAccount.getId(), toAccount.getId());

                // Deduct the amount from the sender's account
                fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
                log.info("Deducted {} from account {}", transaction.getAmount(), fromAccount.getId());

                // Add the amount to the beneficiary's account
                toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
                log.info("Deposited {} to account {}", transaction.getAmount(), toAccount.getId());

                // Update transaction status
                transaction.setStatus("PAYMENT COMPLETE");

                // Save changes to the accounts and transaction
                accountRepository.save(fromAccount);
                accountRepository.save(toAccount);
                transactionRepository.save(transaction);

                log.info("Transaction {} processed and status set to PAYMENT COMPLETE", transaction.getTransactionId());
            }
        }
    }
}

