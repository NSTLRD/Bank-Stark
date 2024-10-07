/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_beneficiaries")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBeneficiary {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "beneficiary_user_id", nullable = false)
    private String beneficiaryUserId;

    @Column(name = "beneficiary_id", nullable = false)
    private String beneficiaryId;

    @Column(name = "beneficiary_email", nullable = false)
    private String beneficiaryEmail;

    @Column(name = "account_owner_id", nullable = false)
    private String accountOwnerId;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
