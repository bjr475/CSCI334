package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public abstract class UserModel {
    private String userID;
    private Date createdTime;
    private StringProperty email;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty mobile;
    private StringProperty password;
    private StringProperty permissions;

    public UserModel() {
        userID = "UNKNOWN";
        createdTime = new Date();
        email = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        mobile = new SimpleStringProperty();
        password = new SimpleStringProperty();
        permissions = new SimpleStringProperty();
    }

    public UserModel(String userID, Date createdTime, String email, String firstName, String lastName, String mobile,
                     String password, String permissions) {
        this.userID = userID;
        this.createdTime = createdTime;
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.mobile = new SimpleStringProperty(mobile);
        this.password = new SimpleStringProperty(password);
        this.permissions = new SimpleStringProperty(permissions);
    }

    public UserModel(UserModel model) {
        this.userID = model.userID;
        this.createdTime = model.createdTime;
        this.email = new SimpleStringProperty(model.email.get());
        this.firstName = new SimpleStringProperty(model.firstName.get());
        this.lastName = new SimpleStringProperty(model.lastName.get());
        this.mobile = new SimpleStringProperty(model.mobile.get());
        this.password = new SimpleStringProperty(model.password.get());
        this.permissions = new SimpleStringProperty(model.permissions.get());
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getMobile() {
        return mobile.get();
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getPermissions() {
        return permissions.get();
    }

    public void setPermissions(String permissions) {
        this.permissions.set(permissions);
    }

    public StringProperty permissionsProperty() {
        return permissions;
    }

}


