package com.example.application_management_system.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.User;

public class RegistrationCompleteEvent
        extends ApplicationEvent {

    private User user;

    public RegistrationCompleteEvent(User user) {

        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
