package com.github.stan256.bblaccount.event.listener;

import com.github.stan256.bblaccount.event.OnUserAccountChangeEvent;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OnUserAccountChangeListener implements ApplicationListener<OnUserAccountChangeEvent> {
    private final MailService mailService;

    @Autowired
    public OnUserAccountChangeListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    @Async
    public void onApplicationEvent(OnUserAccountChangeEvent onUserAccountChangeEvent) {
        sendAccountChangeEmail(onUserAccountChangeEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendAccountChangeEmail(OnUserAccountChangeEvent event) {
        User user = event.getUser();
        String action = event.getAction();
        String actionStatus = event.getActionStatus();
        String recipientAddress = user.getEmail();

        try {
            mailService.sendAccountChangeEmail(action, actionStatus, recipientAddress);
        } catch (IOException | TemplateException e) {
            log.error(e.toString());
            throw new RuntimeException("Account Change Mail. " + recipientAddress);
        }
    }
}
