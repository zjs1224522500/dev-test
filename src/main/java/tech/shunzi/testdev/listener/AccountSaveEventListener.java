package tech.shunzi.testdev.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tech.shunzi.testdev.model.dto.AccountSaveApplicationEvent;
import tech.shunzi.testdev.model.dto.AccountSaveEvent;

@Component
public class AccountSaveEventListener {

//    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleEvent(AccountSaveEvent accountSaveEvent) throws InterruptedException {
        System.out.println("======================================");
        System.out.println("Listener listened AccountSaveEvent");
        Thread.sleep(3000);
        System.out.println(accountSaveEvent.getEventType());
        System.out.println("======================================");

    }

//    @EventListener(condition = "#accountSaveApplicationEvent.valid")
//    public void handleEvent(AccountSaveApplicationEvent accountSaveApplicationEvent) {
//        System.out.println("======================================");
//        System.out.println("Listener listened AccountSaveApplicationEvent");
//        System.out.println(accountSaveApplicationEvent.getEventType());
//        System.out.println("======================================");
//    }
}
