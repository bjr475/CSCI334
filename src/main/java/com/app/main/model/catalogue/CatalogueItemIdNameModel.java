package com.app.main.model.catalogue;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CatalogueItemIdNameModel {
    private ReadOnlyIntegerProperty id;
    private ReadOnlyStringProperty name;

    public CatalogueItemIdNameModel(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
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

    public String toStringValue() {
        return String.format("%d - %s", id.get(), name.get());
    }
}
