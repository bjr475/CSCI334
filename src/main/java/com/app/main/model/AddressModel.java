package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddressModel {
    private StringProperty address;
    private StringProperty suburb;
    private StringProperty state;
    private StringProperty postcode;

    public AddressModel() {
        this("", "", "", "");
    }

    public AddressModel(String address, String suburb, String state, String postcode) {
        this.address = new SimpleStringProperty(address);
        this.suburb = new SimpleStringProperty(suburb);
        this.state = new SimpleStringProperty(state);
        this.postcode = new SimpleStringProperty(postcode);
    }

    public AddressModel(AddressModel address) {
        this.address = new SimpleStringProperty(address.getAddress());
        this.suburb = new SimpleStringProperty(address.getSuburb());
        this.state = new SimpleStringProperty(address.getState());
        this.postcode = new SimpleStringProperty(address.getPostcode());
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s %s %s",
                getAddress(), getSuburb(),
                getState(), getPostcode()
        );
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
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

    public String format() {
        return String.format("%s %s, %s %s", address.get(), suburb.get(), state.get(), postcode.get());
    }
}
