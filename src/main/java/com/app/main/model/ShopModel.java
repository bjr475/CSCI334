package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ShopModel {
    private StringProperty shopName;
    private AddressModel address;

    public ShopModel() {
        shopName = new SimpleStringProperty();
        address = new AddressModel();
    }

    public ShopModel(String name, String addressLine, String addressSuburb,
                     String addressState, String addressPostcode) {
        this.shopName = new SimpleStringProperty(name);
        this.address = new AddressModel(addressLine, addressSuburb, addressState, addressPostcode);
    }

    public String getShopName() {
        return shopName.get();
    }

    public void setShopName(String shopName) {
        this.shopName.set(shopName);
    }

    public StringProperty shopNameProperty() {
        return shopName;
    }

    public AddressModel getAddress() {
        return address;
    }
}
