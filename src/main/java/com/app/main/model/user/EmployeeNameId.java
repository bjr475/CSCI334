package com.app.main.model.user;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.Contract;

public class EmployeeNameId {
    private ReadOnlyIntegerProperty id;
    private ReadOnlyStringProperty display;
    private ReadOnlyStringProperty firstName;

    public EmployeeNameId(int id, String display, String firstName) {
        this.id = new SimpleIntegerProperty(id);
        this.display = new SimpleStringProperty(display);
        this.firstName = new SimpleStringProperty(firstName);
    }

    @Override
    public String toString() {
        return String.format("EmployeeNameId<%d, %s, %s>", id.get(), firstName.get(), display.get());
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        EmployeeNameId item = obj instanceof EmployeeNameId ? ((EmployeeNameId) obj) : null;
        if (item != null) return item.getId() == getId();
        return super.equals(obj);
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id;
    }

    public String getDisplay() {
        return display.get();
    }

    public ReadOnlyStringProperty displayProperty() {
        return display;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public ReadOnlyStringProperty firstNameProperty() {
        return firstName;
    }
}
