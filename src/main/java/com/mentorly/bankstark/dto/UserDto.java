/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {

    private String id;

    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotBlank(message = "The last name cannot be empty")
    private String lastName;

    @Pattern(regexp = "^[0-9]{9}$", message = "The DNI must have 9 digits")
    @NotBlank(message = "The DNI cannot be empty")
    private String dniNumber;

    @Pattern(regexp = "^[0-9]{9}$", message = "The passport must have 9 digits")
    private String passportNumber;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @NotBlank(message = "The email cannot be empty")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$", message = "The password must have at least 8 characters, including numbers and letters")
    @NotBlank(message = "The password cannot be empty")
    private String password;

    @NotBlank(message = "The account type cannot be empty")
    private String accountType;

    private Set<PhoneDto> phones;


}
