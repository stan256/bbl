package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.PasswordResetToken;
import com.github.stan256.bblaccount.repo.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken createToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String token = UUID.randomUUID().toString();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpirationDate(Instant.now().plusMillis(expiration));
        return passwordResetToken;
    }

    public void verifyExpiration(PasswordResetToken token) {
        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            throw new RuntimeException("Expired token. Please issue a new request. Token: " + token.getToken());
        }
    }
}
