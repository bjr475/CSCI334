package com.app.main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Hashtable;

public class SupplierModel {
    private StringProperty supplierName;
    private AddressModel address;
    private StringProperty creditLine;
    private Hashtable<StringProperty, StringProperty> contactDetails;
    private ArrayList<CatalogueModel> modelList;

    public SupplierModel() {
        supplierName = new SimpleStringProperty();
        address = new AddressModel();
        creditLine = new SimpleStringProperty();
        contactDetails = new Hashtable<StringProperty, StringProperty>();
        modelList = new ArrayList<CatalogueModel>();
    }

    public SupplierModel(String supplierName,
                         String addressLine1, String addressLine2, String addressSuburb, String addressState,
                         String addressPostcode, String creditLine, Hashtable<StringProperty,
            StringProperty> contactDetails, ArrayList<CatalogueModel> modelList) {
        this.supplierName = new SimpleStringProperty(supplierName);
        this.address = new AddressModel(addressLine1, addressLine2, addressSuburb, addressState, addressPostcode);
        this.creditLine = new SimpleStringProperty(creditLine);
        this.contactDetails = new Hashtable<StringProperty, StringProperty>(contactDetails);
        this.modelList = new ArrayList<CatalogueModel>(modelList);
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

    public Hashtable<StringProperty, StringProperty> getcontactDetails() {
        return contactDetails;
    }

    public void setclubMember(StringProperty contactName, StringProperty contactMethod) {
        this.contactDetails.put(contactName, contactMethod);
    }

    // modelList
}
