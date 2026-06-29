package com.home.event.auth;

import com.home.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRegisteredEvent {
    private final User user;

    public UserRegisteredEvent(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
