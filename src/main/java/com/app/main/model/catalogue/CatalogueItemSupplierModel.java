package com.app.main.model.catalogue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CatalogueItemSupplierModel {
    private StringProperty name;
    private DoubleProperty price;
    private BooleanProperty current;

    public CatalogueItemSupplierModel() {
        name = new SimpleStringProperty("Supplier Name");
        price = new SimpleDoubleProperty(16.00);
        current = new SimpleBooleanProperty(false);
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

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public boolean isCurrent() {
        return current.get();
    }

    public void setCurrent(boolean current) {
        this.current.set(current);
    }

    public BooleanProperty currentProperty() {
        return current;
    }
}
