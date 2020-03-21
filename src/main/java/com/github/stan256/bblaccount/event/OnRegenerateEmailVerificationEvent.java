package com.github.stan256.bblaccount.event;

import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.entity.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class OnRegenerateEmailVerificationEvent extends ApplicationEvent {
    private transient UriComponentsBuilder redirectUrl;
    private User user;
    private transient EmailVerificationToken token;

    public OnRegenerateEmailVerificationEvent(User user, UriComponentsBuilder redirectUrl, EmailVerificationToken token) {
        super(user);
        this.user = user;
        this.redirectUrl = redirectUrl;
        this.token = token;
    }
}
