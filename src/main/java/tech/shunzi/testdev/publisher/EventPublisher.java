package tech.shunzi.testdev.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserSaveEvent;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventPublisher {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publish(UserSaveEvent userSaveEvent) {
        context.publishEvent(userSaveEvent);
    }

    public void publishByPublisher(User user) {
        System.out.println("Publish custom event by publisher");
        UserSaveEvent userSaveEvent = new UserSaveEvent(this,user);
        publisher.publishEvent(userSaveEvent);
    }

    public void publishEvents(List<User> userList) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Publish user list event by publisher");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        List<UserSaveEvent> events = userList.stream().map(user -> {
            return new UserSaveEvent(this,user);
        }).collect(Collectors.toList());
        publisher.publishEvent(events);
    }

}
