package com.kedacom.commons.vo;

import java.math.BigDecimal;

public class OrderVo {

    private int num;

    private Long categoryId;

    private Long userId;

    public OrderVo(int num, Long categoryId, Long userId) {
        this.num = num;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public OrderVo() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderVo{" + "number of item =" + num + ", categoryId=" + categoryId + ", userId=" + userId + '}';
    }
}
