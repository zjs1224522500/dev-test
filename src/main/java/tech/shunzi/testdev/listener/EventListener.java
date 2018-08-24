package tech.shunzi.testdev.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import tech.shunzi.testdev.model.dto.UserSaveEvent;
import tech.shunzi.testdev.service.UserService;

@Component
public class EventListener {

    @Autowired
    private UserService userService;

    @Async
    @TransactionalEventListener
    public void handleEvent(UserSaveEvent userSaveEvent)
    {
        System.out.println(userService.findAllUsers());
        System.out.println("Listener received user save event.");
        System.out.println(userSaveEvent.toString());
        System.out.println(userSaveEvent.getUser().toString());
        System.out.println(userSaveEvent.getUser().getName());
    }
}
