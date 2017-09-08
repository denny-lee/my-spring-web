package com.lee;

/**
 * Description:
 * On 2017/9/6 16:50 created by LW
 */
public class OrderStatus {
    private String orderNo;
    private String orderStatus;

    public OrderStatus() {
    }

    public OrderStatus(String orderNo, String orderStatus) {
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
