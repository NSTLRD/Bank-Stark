/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BeneficiaryResponseDto {

    private String id;
    private String beneficiaryUserId;
    private String beneficiaryId;
    private String beneficiaryEmail;
    private String accountId;
    private LocalDateTime addedAt;
    private String accountOwnerId;
}
