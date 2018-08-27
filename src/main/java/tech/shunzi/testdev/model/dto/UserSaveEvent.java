package tech.shunzi.testdev.model.dto;

import org.springframework.context.ApplicationEvent;
import tech.shunzi.testdev.model.User;

public class UserSaveEvent extends ApplicationEvent {

    private User user;

    public UserSaveEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public UserSaveEvent(Object source) {
        super(source);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
