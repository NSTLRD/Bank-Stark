/**
 * @author Starling Diaz on 6/2/2024.
 * @Academy mentorly
 * @version bank-stark 1.0
 * @since 6/2/2024.
 */

package com.mentorly.bankstark.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
