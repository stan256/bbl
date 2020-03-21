package com.github.stan256.bblaccount.event;

import com.github.stan256.bblaccount.model.entity.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class OnUserAccountChangeEvent extends ApplicationEvent {
    private User user;
    private String action;
    private String actionStatus;

    public OnUserAccountChangeEvent(User user, String action, String actionStatus) {
        super(user);
        this.user = user;
        this.action = action;
        this.actionStatus = actionStatus;
    }
}
