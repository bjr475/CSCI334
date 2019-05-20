package com.app.main.model.user;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AUserModel {
    private ReadOnlyIntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty displayName;
    private StringProperty password;
    private StringProperty email;

    public AUserModel(int userId) {
        id = new SimpleIntegerProperty(userId);
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
        displayName = new SimpleStringProperty("");
        password = new SimpleStringProperty("");
        email = new SimpleStringProperty("");
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id;
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

    public String getDisplayName() {
        return displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public StringProperty displayNameProperty() {
        return displayName;
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

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public abstract UserType getUserType();

    public enum UserType {
        ADMIN,
        EMPLOYEE,
    }
}


