/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDto {

    @NotBlank(message = "The phone number cannot be empty")
    private String number;

    private String citycode;

    private String countrycode;
}
