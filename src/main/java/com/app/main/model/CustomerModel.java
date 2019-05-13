package com.app.main.model;

import com.app.component.model.SubjectListCellModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class CustomerModel {
    private String customerID;
    private Date createdTime;
    private StringProperty email;
    private StringProperty firstName;
    private StringProperty lastName;
    private AddressModel address;
    private StringProperty creditLine;
    private StringProperty clubMember;
    private ObjectProperty<ObservableList<SubjectListCellModel>> subjectAreas;
    private ArrayList<String> modelTypes;
    private ObjectProperty<ObservableList<SalesModel>> sales;

    public CustomerModel() {
        customerID = "UNKNOWN";
        createdTime = new Date();
        email = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        address = new AddressModel();
        creditLine = new SimpleStringProperty();
        clubMember = new SimpleStringProperty();
        subjectAreas = new SimpleObjectProperty<>((FXCollections.observableArrayList()));
        modelTypes = new ArrayList<String>();
        sales = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    }

    public CustomerModel(String customerID, Date createdTime, String email, String firstName, String lastName,
                         String addressLine, String addressSuburb, String addressState,
                         String addressPostcode, String creditLine,
                         String clubMember, ObjectProperty<ObservableList<SubjectListCellModel>> subjectAreas, ArrayList<String> modelTypes) {
        this.customerID = customerID;
        this.createdTime = createdTime;
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new AddressModel(addressLine, addressSuburb, addressState, addressPostcode);
        this.creditLine = new SimpleStringProperty(creditLine);
        this.clubMember = new SimpleStringProperty(clubMember);
        this.subjectAreas = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.modelTypes = new ArrayList<String>();
    }

    public void reset() {
        customerID = "UNKNOWN";
        email.setValue("");
        firstName.setValue("");
        lastName.setValue("");
        //address
        creditLine.setValue("");
        clubMember.setValue("");
        //subject area
        //model types
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getCreatedTime() {
        return createdTime;
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

    public AddressModel getAddress() {
        return address;
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

    public String getclubMember() {
        return clubMember.get();
    }

    public void setClubMember(String clubMember) {
        this.clubMember.set(clubMember);
    }

    public StringProperty clubMemberProperty() {
        return clubMember;
    }

    //subjectAreas

    //model types
}
