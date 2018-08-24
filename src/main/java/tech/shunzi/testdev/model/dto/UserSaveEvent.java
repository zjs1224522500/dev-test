package tech.shunzi.testdev.model.dto;

import tech.shunzi.testdev.model.User;

public class UserSaveEvent {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
