/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */

package com.mentorly.bankstark.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "phones")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "phone_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
