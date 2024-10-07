/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDetailsDto {

    private UserDto user;
    private List<AccountDto> accounts;
}
