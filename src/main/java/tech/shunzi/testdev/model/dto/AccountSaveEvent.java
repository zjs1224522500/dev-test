package tech.shunzi.testdev.model.dto;


import tech.shunzi.testdev.model.Account;

public class AccountSaveEvent {

    private Account account;
    private String eventType;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
