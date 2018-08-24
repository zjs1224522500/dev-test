package tech.shunzi.testdev.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.DomainEvents;
import tech.shunzi.testdev.model.dto.UserSaveEvent;
import tech.shunzi.testdev.service.UserService;

import javax.persistence.*;
import java.io.Serializable;

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
    UserSaveEvent publishEvent()
    {
        System.out.println("Start publishing user save event");
        UserSaveEvent userSaveEvent = new UserSaveEvent();
        userSaveEvent.setUser(this);
        return userSaveEvent;
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
}
