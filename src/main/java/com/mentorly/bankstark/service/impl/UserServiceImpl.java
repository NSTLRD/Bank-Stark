/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */

package com.mentorly.bankstark.service.impl;

import com.mentorly.bankstark.constants.EmailTemplateName;
import com.mentorly.bankstark.dto.LoginDto;
import com.mentorly.bankstark.dto.UserDto;
import com.mentorly.bankstark.dto.response.LoginResponseDto;
import com.mentorly.bankstark.dto.response.UserResponseDto;
import com.mentorly.bankstark.entities.Account;
import com.mentorly.bankstark.entities.Phone;
import com.mentorly.bankstark.entities.Token;
import com.mentorly.bankstark.entities.User;
import com.mentorly.bankstark.exception.TokenExpiredException;
import com.mentorly.bankstark.exception.UserAlreadyExistsException;
import com.mentorly.bankstark.exception.UserNotFoundException;
import com.mentorly.bankstark.repository.AccountRepository;
import com.mentorly.bankstark.repository.TokenRepository;
import com.mentorly.bankstark.repository.UserRepository;
import com.mentorly.bankstark.security.JwTService;
import com.mentorly.bankstark.service.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwTService jwTService;

    private final EmailServiceImpl emailService;
    private final AuthenticationManager authenticationManager;


    @Value("${mailing.frontend.activation.activationUrl}")
    private String activationUrl;

    @Override
    public UserResponseDto registerUser(UserDto userDto) throws Exception {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with email: " + userDto.getEmail() + " already exists");
        }

        User user = getUser(userDto);
        userRepository.save(user);

        //send the verification email
        sendVerificationEmail(user);

        //create account and save the account
        Account account = getAccount(userDto, user);
        accountRepository.save(account);

        Set<Phone> phoneSet = getPhones(userDto, user);
        user.setPhones(phoneSet);

        //save and flush the user with the token and associated entities
        userRepository.saveAndFlush(user);

        return new UserResponseDto(user);
    }

    private static Set<Phone> getPhones(UserDto userDto, User user) {
        Set<Phone> phoneSet = userDto.getPhones().stream()
                .map(phoneDto -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneDto.getNumber());
                    phone.setCitycode(phoneDto.getCitycode());
                    phone.setCountrycode(phoneDto.getCountrycode());
                    phone.setUser(user);
                    return phone;
                }).collect(Collectors.toSet());
        return phoneSet;
    }

    private Account getAccount( UserDto userDto, User user) {
        Account account = new Account();
        account.setUser(user);
        account.setAccountType(userDto.getAccountType());
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setBalance(0.0);
        return account;
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        //generate new token
        var newToken = generateAndSaveActivationToken(user);
        user.setToken(newToken);
        userRepository.save(user);
        //send email
        emailService.sendEmail(
                user.getEmail(),
                user.FullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );

    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLastName(userDto.getLastName());
        user.setDniNumber(userDto.getDniNumber());
        user.setPassportNumber(userDto.getPassportNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(false);
        return user;
    }

    @Override
    public String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(45))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    @Override
    public String generateActivationCode(int length) {
        String characters = "123456789";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            result.append(characters.charAt(randomIndex));
        }
        return result.toString();
    }

    @Override
    public LoginResponseDto loginAuthenticate(LoginDto loginDto) {
        if(loginDto.getEmail() == null || loginDto.getEmail().isEmpty()){
            throw new RuntimeException("Email is required");
        }

        if(loginDto.getPassword() == null || loginDto.getPassword().isEmpty()){
            throw new RuntimeException("Password is required");
        }

        try {
         var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
         var user = (User) auth.getPrincipal();
         var claims = new HashMap<String, Object>();
         claims.put("name", user.getName());
         claims.put("email", user.getEmail());
         claims.put("id", user.getId());
         var jwtToken = jwTService.generateToken(claims, user);
         return LoginResponseDto.builder().token(jwtToken).build();
        } catch (AuthenticationException e){
            if(userRepository.findByEmail(loginDto.getEmail()).isPresent()){
                throw new RuntimeException("Password is incorrect");
            } else {
                throw new UserNotFoundException("No user found with the provided email address");
            }
        }
    }

    @Override
    public String activateAccount(String token) throws MessagingException, TokenExpiredException {
        Optional<Token> tokenOptional = Optional.ofNullable(tokenRepository.findByToken(token));
        if(!tokenOptional.isPresent()){
            throw new TokenExpiredException("Invalid Token");
        }

        Token savedToken = tokenOptional.get();
        if(LocalDateTime.now().isAfter(savedToken.getExpiredAt())){
            sendVerificationEmail(savedToken.getUser());
            throw new TokenExpiredException("Activation token has expired. A new token has been sent to the email address.");
        }

        User user = savedToken.getUser();
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setActive(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        return token;
    }
}
