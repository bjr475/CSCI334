package com.app.main.model;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalesModel {
    private CustomerModel customer;
    private SimpleMapProperty<CatalogueModel, Integer> items;
    private StringProperty discount;
    private StringProperty total;
    
    public SalesModel(SimpleMapProperty<CatalogueModel, Integer> items,
                      String discount, String total) {
        this.items = new SimpleMapProperty<>(items);
        this.discount = new SimpleStringProperty(discount);
        this.total = new SimpleStringProperty(total);
    }

}
