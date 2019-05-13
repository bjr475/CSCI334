package com.app.main.model;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class SupplierModel {
    private StringProperty supplierName;
    private AddressModel address;
    private StringProperty creditLine;
    private SimpleMapProperty<StringProperty, StringProperty> contactDetails;
    private ArrayList<CatalogueModel> modelList;

    public SupplierModel() {
        supplierName = new SimpleStringProperty();
        address = new AddressModel();
        creditLine = new SimpleStringProperty();
        contactDetails = new SimpleMapProperty<>();
        modelList = new ArrayList<>();
    }

    public SupplierModel(String supplierName,
                         String addressLine, String addressSuburb, String addressState,
                         String addressPostcode, String creditLine, SimpleMapProperty<StringProperty,
            StringProperty> contactDetails, ArrayList<CatalogueModel> modelList) {
        this.supplierName = new SimpleStringProperty(supplierName);
        this.address = new AddressModel(addressLine, addressSuburb, addressState, addressPostcode);
        this.creditLine = new SimpleStringProperty(creditLine);
        this.contactDetails = new SimpleMapProperty<>(contactDetails);
        this.modelList = new ArrayList<>(modelList);
    }


    public String getsupplierName() {
        return supplierName.get();
    }

    public void setsupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getcreditLine() {
        return creditLine.get();
    }

    public void setcreditLine(String creditLine) {
        this.creditLine.set(creditLine);
    }

    public StringProperty creditLineProperty() {
        return creditLine;
    }

    public SimpleMapProperty<StringProperty, StringProperty> getcontactDetails() {
        return contactDetails;
    }

    public void setcontactDetails(StringProperty contactName, StringProperty contactMethod) {
        this.contactDetails.put(contactName, contactMethod);
    }

    public ArrayList<CatalogueModel> getmodelList() {
        return modelList;
    }

    public void setmodelList(StringProperty contactName, StringProperty contactMethod) {
        this.contactDetails.put(contactName, contactMethod);
    }
    // modelList
}
