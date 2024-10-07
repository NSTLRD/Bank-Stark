/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    private String fromAccountId;
    private String toAccountId;
    private double amount;
    private String transferType;
}
