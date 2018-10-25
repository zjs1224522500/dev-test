## Spring Event & Application in EMS

### Observer Design Pattern
#### Scenario
- Elvis, James  and Ervin are looking for a job. And they all send their resume to SAP. The SAP will **make an announcement, arrange interviews and send notification to them**. So three guys will take the interviews.

#### Design
##### Model
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Observer.jpg)

##### Abstract
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/UML%20Observer.jpg)

#### Source code
##### Company.java#notifyObserver()
```Java
    @Override
    public void notifyObserver() {
        employees.forEach(employee -> employee.takeAction(announcement));
    }

    public void announcenMsg(String msg) {
        this.setAnnouncement(msg);
        notifyObserver();
    }
```
##### Employee.java#takeAction(String msg)
```Java
    @Override
    public void takeAction(String msg) {
        this.msg = msg;
        interview();
    }

    public void interview() {
        System.out.println(name + " received msg : " + msg + " and will go to interview");
    }
```

#### Differences with Publish-Subscribe
![image](http://5b0988e595225.cdn.sohucs.com/images/20171128/7c5a95aa8ee348d9b7dff8417e4edb4e.jpeg)

### Spring Events
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Spring%20events.png)

#### Event Publisher
##### 1. @DomainEvents  - Spring Data JPA
- Annotate method in **Domain Model** class. (**DDD**)
- Publish single or multi events with return value of the method
- Method invoking time: Spring Data repository's `save(…)` methods is called.
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

### Application in EMS
#### Demand
- Config event type, condition, and notification details for obserer model.
- Create event monitor log and send notification when condition match.
    - Entitlement/Consumption Save Event
    - Entitlement/Consumption Become Valid/Invalid Event
- Send notificaiton and update event log status in outbound transaction.


#### Difficulties (Some of difficulties are also problems in EMS)
- The same domain model classes are defined in many services.
- The ways to save entitlement/consumption are divided into `JpaRepository.save(...)` and **native sql** (`INSERT/UPDATE ...` and some **Stored Procedures**) execution.
- The Entitlement/Consumption save operation exists in many services.
- Common event trigger, event mnonitor create and send notification logic need to be abstracted in common lib.
    - MQ sender interface and implementation class.
    - Maven circular dependency - Security Enterprise Feature Util.
    - Async process - Multi tenant and authorization for feign call.
- Performance:
    - Guava cache for event condition query.
    - Collect data to reduce feign call times.
    - Process job notifictaions in batch.


#### Design
##### Event Trigger and Create event monitor log
- Entitlement/Consumption Save Event

![image](https://github.wdf.sap.corp/raw/I348910/Session/master/Save%20Event.png)

- Entitlement/Consumption Become Valid/Invalid Event
![image](https://github.wdf.sap.corp/raw/I348910/Session/master/Valid%20or%20Invalid%20Event.png)

##### OutboundProcessor 

![image](https://github.wdf.sap.corp/raw/I348910/Session/master/Event%20notification%20in%20EMS.jpg)

##### Reference :
[[1] Baeldung: How to use events in Spring](https://www.baeldung.com/spring-events)  
[[2] Spring Blog: Better application events in Spring Framework 4.2
](https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2)  
[[3] Zoltan Altfatter: Publishing domain events from aggregate roots](https://zoltanaltfatter.com/2017/06/09/publishing-domain-events-from-aggregate-roots/)  
[[4] Spring IO: Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
[[5] Pursue: Simple Analysis Domain Driven Design](https://www.jianshu.com/p/b6ec06d6b594)  
[[6] Spring IO: Spring 5.1.1 RELEASE Reference Events](https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#context-functionality-events)  
[[7] luohanguo: The Obeserver Design Pattern](https://www.cnblogs.com/luohanguo/p/7825656.html)  
[[8] miaoyu: Differences between Observer and Publish-Subscribe Pattern](https://www.sohu.com/a/207062452_464084)  
[[9] Github Source Code Example of Spring Events by Elvis](https://github.com/zjs1224522500/dev-test)  
[[10] Github Source Code Example of Observer Design Pattern by Elvis](https://github.com/zjs1224522500/data-structure-and-arithmetic/tree/master/design_patterns/src/me/elvis/common/design/observer/another)
