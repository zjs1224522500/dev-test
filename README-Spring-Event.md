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
```
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
- Publisher can publish any Object as Event, because Spring will help to use **PayloadApplicationEvent** to package the object.


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

#### Explore
##### How to event process method in listener class
- class **SimpleApplicationEventMulticaster**
```Java
    @Override
	public void multicastEvent(ApplicationEvent event) {
		multicastEvent(event, resolveDefaultEventType(event));
	}

	@Override
	public void multicastEvent(final ApplicationEvent event, ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
		for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
			Executor executor = getTaskExecutor();
			if (executor != null) {
			    // new Runnable(){...} just define Runnable variable  
				executor.execute(new Runnable() {
					@Override
					public void run() {
						invokeListener(listener, event);
					}
				});
			}
			else {
				invokeListener(listener, event);
			}
		}
	}
```
- As for **executor** in **SimpleApplicationEventMulticaster**, it is **global executor** for all Spring Event Listeners. So if we config an async executor and inject it to multicaster, all listeners will process event async.
```Java
@Configuration
public class AsynchronousSpringEventsConfig implements AsyncConfigurer {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();
        // Inject the ThreadPoolTaskExecutor
        eventMulticaster.setTaskExecutor(getAsyncExecutor());
        return eventMulticaster;
    }


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();

        return taskExecutor;
    }
}
```
- But if we want only several listeners to process event async, we can use **@Async** annotation and add some thread pool config. And **@Async** uses **CglibAopDynamicProxy** and **AsyncExecutionInterceptor** to find suitable executors to execute the annotated method.
```Java
public class AsyncExecutionInterceptor extends ... {
    @Nullable
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        // Find executors configured in configuration class.
        AsyncTaskExecutor executor = this.determineAsyncExecutor(userDeclaredMethod);
        if (executor == null) {
            throw new IllegalStateException("No executor specified and no default executor set on AsyncExecutionInterceptor either");
        } else {
            Callable<Object> task = () -> {
                try {
                    Object result = invocation.proceed();
                    if (result instanceof Future) {
                        return ((Future)result).get();
                    }
                } catch (ExecutionException var4) {
                    this.handleError(var4.getCause(), userDeclaredMethod, invocation.getArguments());
                } catch (Throwable var5) {
                    this.handleError(var5, userDeclaredMethod, invocation.getArguments());
                }

                return null;
            };
            return this.doSubmit(task, executor, invocation.getMethod().getReturnType());
        }
    }
}
```

- How does ThreadPoolTaskExecutor execute method async, we can refer the source code in **ThreadPoolExecutor.class**
```Java
    /**
     * Executes the given task sometime in the future.  The task
     * may execute in a new thread or in an existing pooled thread.
     *
     * If the task cannot be submitted for execution, either because this
     * executor has been shutdown or because its capacity has been reached,
     * the task is handled by the current {@code RejectedExecutionHandler}.
     *
     * @param command the task to execute
     * @throws RejectedExecutionException at discretion of
     *         {@code RejectedExecutionHandler}, if the task
     *         cannot be accepted for execution
     * @throws NullPointerException if {@code command} is null
     */
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }
```


##### Where to call method multicastEvent()
- abstract class **AbstractApplicationContext** 
```Java
	protected void publishEvent(Object event, ResolvableType eventType) {
		Assert.notNull(event, "Event must not be null");
		if (logger.isTraceEnabled()) {
			logger.trace("Publishing event in " + getDisplayName() + ": " + event);
		}

		// Decorate event as an ApplicationEvent if necessary
		ApplicationEvent applicationEvent;
		if (event instanceof ApplicationEvent) {
			applicationEvent = (ApplicationEvent) event;
		}
		else {
			applicationEvent = new PayloadApplicationEvent<Object>(this, event);
			if (eventType == null) {
				eventType = ((PayloadApplicationEvent) applicationEvent).getResolvableType();
			}
		}

		// Multicast right now if possible - or lazily once the multicaster is initialized
		if (this.earlyApplicationEvents != null) {
			this.earlyApplicationEvents.add(applicationEvent);
		}
		else {
			getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
		}

		// Publish event via parent context as well...
		if (this.parent != null) {
			if (this.parent instanceof AbstractApplicationContext) {
				((AbstractApplicationContext) this.parent).publishEvent(event, eventType);
			}
			else {
				this.parent.publishEvent(event);
			}
		}
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


#### Practice
##### 1. Config
- Config communication channel/background job.
- Config event type, conditions and notification ways.

##### 2. Update/Check
- Update entitlement/consumption (manual edit/API)
- Background jobs check entitlement/consumption valid/invalid.

##### 3. Event Trigger & Notification Send
- Check event monitor log status.
- Check outbound monitor log.
- Check outbound destination output.


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
[[11] CSDN Blog: Spring Event System](https://blog.csdn.net/caihaijiang/article/details/7460888)
