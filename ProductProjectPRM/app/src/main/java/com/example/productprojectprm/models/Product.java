package com.example.productprojectprm.models;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Product {
    @SerializedName("product_image")
    private String imgId;
    private String productId;
    @SerializedName("product_name")
    private String productName;
    private int Quantity;
    @SerializedName("list_price")
    private int Price;
    @SerializedName("model_year")
    private int ModelYear;
    @SerializedName("category_id")
    private int category;
    private String maker;
    private String description;
    private int position;

    public Product(String imgId, String productId, String productName, int quantity, int price, String maker, String description, int modelYear, int category, int position) {
        this.imgId = imgId;
        this.productId = productId;
        this.productName = productName;
        Quantity = quantity;
        Price = price;
        this.maker = maker;
        this.description = description;
        ModelYear = modelYear;
        this.category = category;
        this.position = position;

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getModelYear() {
        return ModelYear;
    }

    public void setModelYear(int modelYear) {
        ModelYear = modelYear;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
