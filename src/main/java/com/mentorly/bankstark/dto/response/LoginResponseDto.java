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

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private String token;

}