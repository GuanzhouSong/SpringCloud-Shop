package com.kedacom.order.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datetime")
    private Date datetime;

    @Column(name = "num")
    private int num;

    @Column(name = "summoney")
    private BigDecimal summoney;

    @Column(name = "state")
    private int state;

    @Column(name = "user_id")
    private Long userId;


    public Order() {
    }

    public Order(Date datetime, BigDecimal summoney, int state, Long userId, int num) {
        this.datetime = datetime;
        this.summoney = summoney;
        this.state = state;
        this.userId = userId;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getSummoney() {
        return summoney;
    }

    public void setSummoney(BigDecimal summoney) {
        this.summoney = summoney;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", datetime=" + datetime + ", summoney=" + summoney + ", state=" + state
                + ", userId=" + userId + '}';
    }
}
