package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddressModel {
    private StringProperty line1;
    private StringProperty line2;
    private StringProperty suburb;
    private StringProperty state;
    private StringProperty postcode;

    AddressModel() {
        line1 = new SimpleStringProperty();
        line2 = new SimpleStringProperty();
        suburb = new SimpleStringProperty();
        state = new SimpleStringProperty();
        postcode = new SimpleStringProperty();
    }

    AddressModel(String addressLine1, String addressLine2, String addressSuburb, String addressState, String addressPostcode) {
        line1 = new SimpleStringProperty(addressLine1);
        line2 = new SimpleStringProperty(addressLine2);
        suburb = new SimpleStringProperty(addressSuburb);
        state = new SimpleStringProperty(addressState);
        postcode = new SimpleStringProperty(addressPostcode);
    }

    public String getLine1() {
        return line1.get();
    }

    public void setLine1(String line1) {
        this.line1.set(line1);
    }

    public StringProperty line1Property() {
        return line1;
    }

    public String getLine2() {
        return line2.get();
    }

    public void setLine2(String line2) {
        this.line2.set(line2);
    }

    public StringProperty line2Property() {
        return line2;
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
