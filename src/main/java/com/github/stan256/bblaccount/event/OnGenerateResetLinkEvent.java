package com.github.stan256.bblaccount.event;

import com.github.stan256.bblaccount.model.entity.PasswordResetToken;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class OnGenerateResetLinkEvent extends ApplicationEvent {

    private transient UriComponentsBuilder redirectUrl;
    private transient PasswordResetToken passwordResetToken;

    public OnGenerateResetLinkEvent(PasswordResetToken passwordResetToken, UriComponentsBuilder redirectUrl) {
        super(passwordResetToken);
        this.passwordResetToken = passwordResetToken;
        this.redirectUrl = redirectUrl;
    }
}
