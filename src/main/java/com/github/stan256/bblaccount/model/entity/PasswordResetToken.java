package com.github.stan256.bblaccount.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
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
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expirationDate;
}
