package com.github.stan256.bblaccount.model.entity;

import com.github.stan256.bblaccount.model.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity(name = "email_verification_token")
public class EmailVerificationToken extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_id")
    @SequenceGenerator(name = "email_token_id", sequenceName = "email_verification_token_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(nullable = false)
    private Instant expirationDate;
}
