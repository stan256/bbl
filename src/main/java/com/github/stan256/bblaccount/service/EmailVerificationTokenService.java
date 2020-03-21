package com.github.stan256.bblaccount.service;


import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.TokenStatus;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.repo.EmailVerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmailVerificationTokenService {
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Value("${app.token.email.verification.duration}")
    private Long emailVerificationTokenExpiryDuration;

    @Autowired
    public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public void createVerificationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setTokenStatus(TokenStatus.PENDING);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpirationDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        log.info("Generated Email verification token [" + emailVerificationToken + "]");
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.PENDING);
        existingToken.setExpirationDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        log.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    public Optional<EmailVerificationToken> findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void verifyExpiration(EmailVerificationToken token) {
        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            throw new RuntimeException("Expired token. Please issue a new request. Token: " + token.getToken());
        }
    }

}

