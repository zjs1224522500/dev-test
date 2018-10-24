package tech.shunzi.testdev.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tech.shunzi.testdev.model.Account;
import tech.shunzi.testdev.model.dto.AccountSaveApplicationEvent;
import tech.shunzi.testdev.model.dto.AccountSaveEvent;

@Component
public class AccountEventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishEventByContext(Account account)
    {
        AccountSaveEvent event = new AccountSaveEvent();
        event.setAccount(account);
        event.setEventType("AccountSaveEventByContext");
        System.out.println("====================================");
        System.out.println("Start to publish AccountSaveEvent by context");
        applicationContext.publishEvent(event);
        System.out.println("End");
        System.out.println("====================================");
    }

    public void publishEventByPublisher(Account account)
    {
        AccountSaveEvent event = new AccountSaveEvent();
        event.setAccount(account);
        event.setEventType("AccountSaveEventByPublisher");
        System.out.println("====================================");
        System.out.println("Start to publish AccountSaveEvent by publisher");
        publisher.publishEvent(event);
        System.out.println("End");
        System.out.println("====================================");
    }

    public void publishApplicationEventByContext(Account account)
    {
        AccountSaveApplicationEvent event = new AccountSaveApplicationEvent(account);
        event.setValid(true);
        event.setEventType("AccountSaveApplicationEventByContext");
        System.out.println("====================================");
        System.out.println("Start to publish AccountSaveApplicationEvent by context");
        applicationContext.publishEvent(event);
        System.out.println("End");
        System.out.println("====================================");
    }

    public void publishApplicationEventByPublisher(Account account)
    {
        AccountSaveApplicationEvent event = new AccountSaveApplicationEvent(account);
        event.setEventType("AccountSaveApplicationEventByPublisher");
        System.out.println("====================================");
        System.out.println("Start to publish AccountSaveApplicationEvent by publisher");
        publisher.publishEvent(event);
        System.out.println("End");
        System.out.println("====================================");
    }
}
