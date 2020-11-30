package com.github.stan256.bblaccount.event.listener;

import com.github.stan256.bblaccount.event.OnUserRegistrationCompleteEvent;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.service.EmailVerificationTokenService;
import com.github.stan256.bblaccount.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OnUserRegistrationCompleteListener {

    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;

    @Autowired
    public OnUserRegistrationCompleteListener(EmailVerificationTokenService emailVerificationTokenService, MailService mailService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    @EventListener
    public void onApplicationEvent(OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent) {
        sendEmailVerification(onUserRegistrationCompleteEvent);
    }

    private void sendEmailVerification(OnUserRegistrationCompleteEvent event) {
        User user = event.getUser();
        log.info("Sending an email for user: " + user);
        String token = emailVerificationTokenService.generateNewToken();
        emailVerificationTokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", token).toUriString();

        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Email Verification: " + recipientAddress, e);
        }
    }
}
