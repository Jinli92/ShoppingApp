package com.example.jinliyu.shoppingapp_1.data;

/**
 * Created by jinliyu on 4/17/18.
 */

public class Order {

    String orderId;
    String orderStatus;
    String shippingName;
    String billingAddress;
    String deliveryAddr;
    String mobile;
    String email;
    String itemId;
    String itemName;
    String itemQuantity;
    String totalprice;
    String paidprice;
    String placedon;

    public Order(String orderId, String orderStatus, String shippingName, String billingAddress, String deliveryAddr, String mobile, String email, String itemId, String itemName, String itemQuantity, String totalprice, String paidprice, String placedon) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.shippingName = shippingName;
        this.billingAddress = billingAddress;
        this.deliveryAddr = deliveryAddr;
        this.mobile = mobile;
        this.email = email;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.totalprice = totalprice;
        this.paidprice = paidprice;
        this.placedon = placedon;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPaidprice() {
        return paidprice;
    }

    public void setPaidprice(String paidprice) {
        this.paidprice = paidprice;
    }

    public String getPlacedon() {
        return placedon;
    }

    public void setPlacedon(String placedon) {
        this.placedon = placedon;
    }
}
