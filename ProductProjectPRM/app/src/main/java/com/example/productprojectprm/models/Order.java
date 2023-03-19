package com.example.productprojectprm.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Order {
    @SerializedName("id")
    private int orderId;
    @SerializedName("user_id")
    private int userId;
    private String userName;
    @SerializedName("product_name1")
    private String productName;
    @SerializedName("product_name2")
    private String productName2;
    @SerializedName("product_image1")
    private String productImage;
    @SerializedName("product_image2")
    private String productImage2;
    @SerializedName("product_quantity1")
    private int quantity;
    @SerializedName("product_quantity2")
    private int quantity2;
    @SerializedName("product_price1")
    private int price;
    @SerializedName("product_price2")
    private int price2;
    @SerializedName("total_price")
    private int totalPrice;
    @SerializedName("order_status")
    private String orderStatus;
    private Date orderDate;


    public Order(int orderId, int userId, String userName, String productName, String productName2, String productImage, String productImage2, int quantity, int quantity2, int price, int price2, int totalPrice, String orderStatus, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.productName = productName;
        this.productName2 = productName2;
        this.productImage = productImage;
        this.productImage2 = productImage2;
        this.quantity = quantity;
        this.quantity2 = quantity2;
        this.price = price;
        this.price2 = price2;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(String productImage2) {
        this.productImage2 = productImage2;
    }

    public int getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(int quantity2) {
        this.quantity2 = quantity2;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
