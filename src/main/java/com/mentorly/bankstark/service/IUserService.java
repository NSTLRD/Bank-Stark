/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */
package com.mentorly.bankstark.service;

import com.mentorly.bankstark.dto.LoginDto;
import com.mentorly.bankstark.dto.UserDto;
import com.mentorly.bankstark.dto.response.LoginResponseDto;
import com.mentorly.bankstark.dto.response.UserResponseDto;
import com.mentorly.bankstark.entities.User;
import com.mentorly.bankstark.exception.TokenExpiredException;
import jakarta.mail.MessagingException;

public interface IUserService {

    UserResponseDto registerUser(UserDto userDto) throws Exception;
    String generateAndSaveActivationToken(User user);
    String generateActivationCode(int length);
    LoginResponseDto loginAuthenticate(LoginDto loginDto);
    String activateAccount(String token) throws MessagingException, TokenExpiredException;
}
