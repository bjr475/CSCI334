package com.app.main.model.sales;

import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SaleItemModel {
    private CatalogueItemIdNameModel item;
    private IntegerProperty quantity;
    private DoubleProperty discount;

    public SaleItemModel() {
        item = null;
        quantity = new SimpleIntegerProperty(0);
        discount = new SimpleDoubleProperty(0);
    }

    public CatalogueItemIdNameModel getItem() {
        return item;
    }

    public void setItem(CatalogueItemIdNameModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public DoubleProperty discountProperty() {
        return discount;
    }
}
