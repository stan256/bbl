package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "refresh_token")
public class RefreshToken extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_id")
    @SequenceGenerator(name = "refresh_token_id", sequenceName = "refresh_token_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private UserDevice userDevice;

    @Column
    private Long refreshCount;

    @Column(nullable = false)
    private Instant expirationDate;

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}
