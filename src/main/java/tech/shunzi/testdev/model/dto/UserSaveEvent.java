package tech.shunzi.testdev.model.dto;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationEvent;
import tech.shunzi.testdev.model.User;

public class UserSaveEvent extends ApplicationEvent {

    private User user;

    private JSONObject value;

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

    public JSONObject getValue() {
        return value;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }
}
