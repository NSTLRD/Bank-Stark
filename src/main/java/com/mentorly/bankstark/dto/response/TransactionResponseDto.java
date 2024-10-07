/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDto {

    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private String status;
    private String transferType;
    private double amount;
    private LocalDateTime createdAt;
}
