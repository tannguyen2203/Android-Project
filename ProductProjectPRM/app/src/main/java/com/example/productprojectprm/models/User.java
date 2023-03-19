package com.example.productprojectprm.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User {
    @SerializedName("id")
    private int userId;
    @SerializedName("user_account")
    private String userAccount;
    @SerializedName("user_password")
    private String userPassword;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_role")
    private String userRole;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;

    public User(int userId, String userAccount, String userPassword, String userName, String userRole) {
        this.userId = userId;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = userRole;
    }

    public User(int userId, String userAccount, String userPassword, String userName, String userRole, String phone, String address) {
        this.userId = userId;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = userRole;
        this.phone = phone;
        this.address = address;
    }

    public User() {

    }

    public User(String userAccount){
        this.userAccount = userAccount;
    }

    public User(String userAccount, String userPassword, String phone,String userName,String address, String userRole){
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.phone = phone;
        this.userName = userName;
        this.address = address;
        this.userRole = "user";
    }

    public User(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
