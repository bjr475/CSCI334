package com.app.main.model.supplier;

import com.app.main.model.AddressModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class SupplierModel {
    private StringProperty creditLine;
    private StringProperty name;
    private ObjectProperty<AddressModel> address;
    private ObjectProperty<ObservableList<SupplierItemModel>> models;
    private ObjectProperty<ObservableList<SupplierContactModel>> contactDetails;

    private ObjectProperty<SupplierContactModel> primaryContact;

    public SupplierModel() {
        creditLine = new SimpleStringProperty("");
        name = new SimpleStringProperty("");
        address = new SimpleObjectProperty<>(new AddressModel());
        models = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        contactDetails = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        primaryContact = new SimpleObjectProperty<>(new SupplierContactModel());
        contactDetails.addListener(this::contactsChanged);
        contactDetails.get().addListener((ListChangeListener<SupplierContactModel>) c -> updatePrimary());
    }

    private void contactsChanged(@SuppressWarnings("unused") ObservableValue<? extends ObservableList<SupplierContactModel>> observable, ObservableList<SupplierContactModel> oldValue, ObservableList<SupplierContactModel> newValue) {
        updatePrimary();
        if (newValue != null) newValue.addListener((ListChangeListener<SupplierContactModel>) c -> updatePrimary());
    }

    private void updatePrimary() {
        for (SupplierContactModel contactModel : contactDetails.get()) {
            if (contactModel.isPrimary()) primaryContact.set(contactModel);
        }
    }

    public String getCreditLine() {
        return creditLine.get();
    }

    public void setCreditLine(String creditLine) {
        this.creditLine.set(creditLine);
    }

    public StringProperty creditLineProperty() {
        return creditLine;
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

    public AddressModel getAddress() {
        return address.get();
    }

    public void setAddress(AddressModel address) {
        this.address.set(address);
    }

    public ObjectProperty<AddressModel> addressProperty() {
        return address;
    }

    public ObservableList<SupplierItemModel> getModels() {
        return models.get();
    }

    public void setModels(ObservableList<SupplierItemModel> models) {
        this.models.set(models);
    }

    public ObjectProperty<ObservableList<SupplierItemModel>> modelsProperty() {
        return models;
    }

    public ObservableList<SupplierContactModel> getContactDetails() {
        return contactDetails.get();
    }

    public void setContactDetails(ObservableList<SupplierContactModel> contactDetails) {
        this.contactDetails.set(contactDetails);
    }

    public ObjectProperty<ObservableList<SupplierContactModel>> contactDetailsProperty() {
        return contactDetails;
    }

    public SupplierContactModel getPrimaryContact() {
        return primaryContact.get();
    }

    public ObjectProperty<SupplierContactModel> primaryContactProperty() {
        return primaryContact;
    }
}
