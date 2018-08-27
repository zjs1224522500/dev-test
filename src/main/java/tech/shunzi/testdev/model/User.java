package tech.shunzi.testdev.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import tech.shunzi.testdev.model.dto.UserSaveEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_User")
public class User implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "c_guid")
    private String guid;

    @Column(name = "c_id")
    private Integer id;

    @Column(name ="c_name", length = 20)
    private String name;

    @Column(name = "c_desc")
    private String desc;

    @DomainEvents
    List<UserSaveEvent> publishEvent()
    {
        System.out.println("Start publishing user save event");

        UserSaveEvent userSaveEvent = new UserSaveEvent(this,this);
        System.out.println(userSaveEvent.getUser());
        UserSaveEvent testAnotherEvent = new UserSaveEvent(this,this);
        List<UserSaveEvent> events = new ArrayList<>();
        events.add(userSaveEvent);
        events.add(testAnotherEvent);
        return events;
    }

    @AfterDomainEventPublication
    void afterPublish()
    {
        System.out.println("After publishing user save event");
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "User{" +
                "guid='" + guid + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
