package tech.shunzi.testdev.model.dto;

import java.util.Objects;

public class UserDto {

    private int id;

    private String name;

    private String introduction;

    public int groupNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                groupNo == userDto.groupNo &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(introduction, userDto.introduction);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, introduction, groupNo);
    }
}
