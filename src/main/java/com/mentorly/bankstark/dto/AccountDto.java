/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private String id;
    private String userId;
    private String accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double balance;
    private String beneficiaryId;
    private String beneficiaryEmail;
}
