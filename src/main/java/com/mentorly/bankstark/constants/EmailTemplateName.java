/**
 * @author Starling Diaz on 7/31/2024.
 * @Academy @Academy mentorly
 * @version bank-stark 1.0
 * @since 7/31/2024.
 */
package com.mentorly.bankstark.constants;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

        ACTIVATE_ACCOUNT("activate_account"),
        RESET_PASSWORD("reset-password");

        private final String name;

        EmailTemplateName(String name) {
            this.name = name;
        }
}
