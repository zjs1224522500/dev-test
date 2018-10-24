package tech.shunzi.testdev.model.dto;

import org.springframework.context.ApplicationEvent;

public class AccountSaveApplicationEvent extends ApplicationEvent {


    private String eventType;

    private boolean valid;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AccountSaveApplicationEvent(Object source) {
        super(source);
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
