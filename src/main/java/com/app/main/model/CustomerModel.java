package com.app.main.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class CustomerModel {
    private IntegerProperty id;
    private StringProperty email;
    private StringProperty firstName;
    private StringProperty lastName;
    private DoubleProperty creditLine;
    private BooleanProperty clubMember;
    private ObjectProperty<Date> createdTime;
    private ObjectProperty<AddressModel> address;
    private ObjectProperty<ObservableList<SalesModel>> sales;
    private ObjectProperty<ObservableList<String>> modelTypes;
    private ObjectProperty<ObservableList<String>> subjectAreas;

    public CustomerModel() {
        id = new SimpleIntegerProperty(0);
        email = new SimpleStringProperty("");
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
        creditLine = new SimpleDoubleProperty(0);
        clubMember = new SimpleBooleanProperty(false);
        createdTime = new SimpleObjectProperty<>(null);
        address = new SimpleObjectProperty<>(new AddressModel());
        sales = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        modelTypes = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        subjectAreas = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    }

    @Override
    public String toString() {
        return String.format(
                "CustomerModel<%d %s %s %s %.02f %b %s %s>",
                id.get(), email.get(), firstName.get(), lastName.get(), creditLine.get(), clubMember.get(),
                modelTypes.get().toArray(), subjectAreas.get().toArray()
        );
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public double getCreditLine() {
        return creditLine.get();
    }

    public void setCreditLine(double creditLine) {
        this.creditLine.set(creditLine);
    }

    public DoubleProperty creditLineProperty() {
        return creditLine;
    }

    public boolean isClubMember() {
        return clubMember.get();
    }

    public void setClubMember(boolean clubMember) {
        this.clubMember.set(clubMember);
    }

    public BooleanProperty clubMemberProperty() {
        return clubMember;
    }

    public Date getCreatedTime() {
        return createdTime.get();
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime.set(createdTime);
    }

    public ObjectProperty<Date> createdTimeProperty() {
        return createdTime;
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

    public ObservableList<SalesModel> getSales() {
        return sales.get();
    }

    public void setSales(ObservableList<SalesModel> sales) {
        this.sales.set(sales);
    }

    public ObjectProperty<ObservableList<SalesModel>> salesProperty() {
        return sales;
    }

    public ObservableList<String> getModelTypes() {
        return modelTypes.get();
    }

    public void setModelTypes(ObservableList<String> modelTypes) {
        this.modelTypes.set(modelTypes);
    }

    public ObjectProperty<ObservableList<String>> modelTypesProperty() {
        return modelTypes;
    }

    public ObservableList<String> getSubjectAreas() {
        return subjectAreas.get();
    }

    public void setSubjectAreas(ObservableList<String> subjectAreas) {
        this.subjectAreas.set(subjectAreas);
    }

    public ObjectProperty<ObservableList<String>> subjectAreasProperty() {
        return subjectAreas;
    }
}
