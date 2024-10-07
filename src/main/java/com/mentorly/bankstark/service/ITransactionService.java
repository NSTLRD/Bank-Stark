/**
 * @author Starling Diaz on 8/7/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 8/7/2024.
 */
package com.mentorly.bankstark.service;

import com.mentorly.bankstark.dto.request.TransactionRequestDto;
import com.mentorly.bankstark.dto.response.TransactionResponseDto;

import java.util.List;

public interface ITransactionService {

    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);

    List<TransactionResponseDto> getTransactionsByAccountId(String accountId);
}
