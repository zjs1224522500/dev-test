### Spring Event & Application

![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Spring%20events.png)

#### Event Publisher
##### 1. @DomainEvents  - Spring Data JPA
- Annotate method in **Domain Model** class.
- Publish single or multi events with return value of the method
- Method invoking time: Spring Data repository’s `save(…)` methods is called.
- `@AfterDomainEventPublication` is used to potentially clean the list of events to be published (among other uses) when `@DomainEvents` exists.

```Java
    @DomainEvents
    AccountSaveEvent accountSaveEvent()
    {
        AccountSaveEvent accountSaveEvent = new AccountSaveEvent();
        accountSaveEvent.setAccount(this);
        accountSaveEvent.setEventType("AccountSaveEventByJPA");
        return accountSaveEvent;
    }

    @AfterDomainEventPublication
    void callbackMethod() {
        System.out.println("DATA SAVED!\n"+"WELL DONE");
    }
```

##### 2. Mannual Publish
- **ApplicationEventPublisher**
- **ApplicationContext**
    - Extends interface **ApplicationEventPublisher**

```Java
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
}
```

#### Event Listener
##### 1. @EventListener
- Annotate method.
- Support **SpEL** for event condition match.
- Can listen multi event class.

```Java
    @EventListener(condition = "#accountSaveApplicationEvent.valid")
    public void handleEvent(AccountSaveApplicationEvent accountSaveApplicationEvent) {
        System.out.println("======================================");
        System.out.println("Listener listened AccountSaveApplicationEvent");
        System.out.println(accountSaveApplicationEvent.getEventType());
        System.out.println("======================================");
    }
```

##### 2. @TransactionEventListener
- Base on **@EventListener**.
- phase : Bind the handling of an event to **Transaction lifecycle**.
- fallbackExecution: Config **Transaction** if mandatory for this event trigger.
```Java
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleEvent(AccountSaveEvent accountSaveEvent) {
        System.out.println("======================================");
        System.out.println("Listener listened AccountSaveEvent");
        System.out.println(accountSaveEvent.getEventType());
        System.out.println("======================================");
    }
```


---
##### Reference :
[[1] Baeldung: How to use events in Spring](https://www.baeldung.com/spring-events)  
[[2] Spring Blog: Better application events in Spring Framework 4.2
](https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2)  
[[3] Zoltan Altfatter: Publishing domain events from aggregate roots](https://zoltanaltfatter.com/2017/06/09/publishing-domain-events-from-aggregate-roots/)  
[[4] Spring IO: Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
[[5] Jianshu: Domain Driven Design](https://www.jianshu.com/p/b6ec06d6b594)  
[[6] Github Source Code Example of Elvis](https://github.com/zjs1224522500/dev-test)
