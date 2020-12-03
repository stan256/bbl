package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.Instant;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pwd_reset_token_id")
    @SequenceGenerator(name = "pwd_reset_token_id", sequenceName = "pwd_reset_token_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expirationDate;
}
