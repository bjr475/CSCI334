package com.app.main.model.supplier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SupplierContactModel {
    private StringProperty name;
    private StringProperty email;
    private StringProperty phone;
    private BooleanProperty primary;

    public SupplierContactModel() {
        name = new SimpleStringProperty("");
        email = new SimpleStringProperty("");
        phone = new SimpleStringProperty("");
        primary = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
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

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public boolean isPrimary() {
        return primary.get();
    }

    public void setPrimary(boolean primary) {
        this.primary.set(primary);
    }

    public BooleanProperty primaryProperty() {
        return primary;
    }
}
