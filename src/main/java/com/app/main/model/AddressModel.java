package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddressModel {
    private StringProperty address;
    private StringProperty suburb;
    private StringProperty state;
    private StringProperty postcode;

    AddressModel() {
        address = new SimpleStringProperty();
        suburb = new SimpleStringProperty();
        state = new SimpleStringProperty();
        postcode = new SimpleStringProperty();
    }

    AddressModel(String addressLine, String addressSuburb, String addressState, String addressPostcode) {
        address = new SimpleStringProperty(addressLine);
        suburb = new SimpleStringProperty(addressSuburb);
        state = new SimpleStringProperty(addressState);
        postcode = new SimpleStringProperty(addressPostcode);
    }

    public String getLine1() {
        return address.get();
    }

    public void setLine1(String line1) {
        this.address.set(line1);
    }

    public StringProperty line1Property() {
        return address;
    }

    public String getSuburb() {
        return suburb.get();
    }

    public void setSuburb(String suburb) {
        this.suburb.set(suburb);
    }

    public StringProperty suburbProperty() {
        return suburb;
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public StringProperty stateProperty() {
        return state;
    }

    public String getPostcode() {
        return postcode.get();
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }
}
