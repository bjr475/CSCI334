package com.app.main.model.catalogue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

public class CatalogueItemSupplierModel {
    private StringProperty name;
    private DoubleProperty price;

    public CatalogueItemSupplierModel() {
        name = new SimpleStringProperty("Supplier Name");
        price = new SimpleDoubleProperty(16.00);
    }

    CatalogueItemSupplierModel(@NotNull CatalogueItemSupplierModel item) {
        name = new SimpleStringProperty(item.name.get());
        price = new SimpleDoubleProperty(item.price.get());
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
}
