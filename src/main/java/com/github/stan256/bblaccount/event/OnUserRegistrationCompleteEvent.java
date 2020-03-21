package com.github.stan256.bblaccount.event;

import com.github.stan256.bblaccount.model.entity.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class OnUserRegistrationCompleteEvent extends ApplicationEvent {
    private transient UriComponentsBuilder redirectUrl;
    private User user;

    public OnUserRegistrationCompleteEvent(User user, UriComponentsBuilder redirectUrl) {
        super(user);
        this.user = user;
        this.redirectUrl = redirectUrl;
    }
}
