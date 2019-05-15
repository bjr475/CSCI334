package com.app.main.model;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalesModel {
    private String saleID;
    private CustomerModel customer;
    private SimpleMapProperty<CatalogueModel, Integer> items;
    private StringProperty discount;
    private StringProperty total;

    public SalesModel(String saleID, SimpleMapProperty<CatalogueModel, Integer> items,
                      String discount, String total) {
        this.saleID = saleID;
        this.items = new SimpleMapProperty<>(items);
        this.discount = new SimpleStringProperty(discount);
        this.total = new SimpleStringProperty(total);
    }

    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public CustomerModel getAddress() {
        return customer;
    }

    //items

    public String getDiscount() {
        return discount.get();
    }

    public void setdiscount(String discount) {
        this.discount.set(discount);
    }

    public StringProperty discountProperty() {
        return discount;
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public StringProperty totalProperty() {
        return total;
    }
}
