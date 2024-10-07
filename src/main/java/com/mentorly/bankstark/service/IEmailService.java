/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */
package com.mentorly.bankstark.service;

import com.mentorly.bankstark.constants.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface IEmailService {

        void sendEmail(String to, String username, EmailTemplateName emailTemplate, String ConfirmationUrl, String activationConde, String subject) throws MessagingException;
}
