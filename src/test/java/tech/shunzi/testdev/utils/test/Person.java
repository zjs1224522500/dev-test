package tech.shunzi.testdev.utils.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    private String name;

    private Integer luckyNo;

    private Double height;

    @JsonFormat(pattern = "yyyyMMdd")
    private Date birthDay;

    private Boolean married;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date bornTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLuckyNo() {
        return luckyNo;
    }

    public void setLuckyNo(Integer luckyNo) {
        this.luckyNo = luckyNo;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public Date getBornTime() {
        return bornTime;
    }

    public void setBornTime(Date bornTime) {
        this.bornTime = bornTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", luckyNo=" + luckyNo +
                ", height=" + height +
                ", birthDay=" + birthDay +
                ", married=" + married +
                ", bornTime=" + bornTime +
                '}';
    }
}
