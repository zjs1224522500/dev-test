package tech.shunzi.testdev.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import tech.shunzi.testdev.model.dto.AccountSaveEvent;

import javax.persistence.*;

@Entity
@Table(name = "t_Account")
public class Account {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "c_guid")
    private String guid;

    @Column(name = "c_id")
    private String name;

    @Column(name = "c_email")
    private String email;

//    @DomainEvents
//    AccountSaveEvent accountSaveEvent()
//    {
//        AccountSaveEvent accountSaveEvent = new AccountSaveEvent();
//        accountSaveEvent.setAccount(this);
//        accountSaveEvent.setEventType("AccountSaveEventByJPA");
//        System.out.println("**********************************************");
//        System.out.println("@DomainEvents starts to publish events.");
//        return accountSaveEvent;
//    }
//
//    @AfterDomainEventPublication
//    void callbackMethod() {
//        System.out.println("**************************************");
//        System.out.println("@AfterDomainEvents");
//        System.out.println("DATA SAVED!\n"+"WELL DONE");
//    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
