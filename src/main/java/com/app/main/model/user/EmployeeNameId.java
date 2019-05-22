package com.app.main.model.user;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeNameId {
    private ReadOnlyIntegerProperty id;
    private ReadOnlyStringProperty name;

    public EmployeeNameId(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String toString() {
        return String.format("EmployeeNameId<%d, %s>", id.get(), name.get());
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }
}
