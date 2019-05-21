package com.app.main.model;

import com.app.main.model.user.permissions.EmployeePermissions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

public class EmployeeTable {
    private ReadOnlyIntegerProperty employeeId;
    private StringProperty displayName;
    private StringProperty firstName;
    private StringProperty contact;
    private StringProperty store;
    private StringProperty position;
    private BooleanProperty createItem;
    private BooleanProperty modifyItem;
    private BooleanProperty viewSale;
    private BooleanProperty createSale;
    private BooleanProperty modifySale;
    private BooleanProperty viewCustomer;
    private BooleanProperty createCustomer;
    private BooleanProperty modifyCustomer;
    private BooleanProperty manageSupplier;
    private BooleanProperty manageEmployee;

    public EmployeeTable(int id) {
        employeeId = new SimpleIntegerProperty(id);
        displayName = new SimpleStringProperty("display");
        firstName = new SimpleStringProperty("First");
        contact = new SimpleStringProperty("employee@toy.com");
        store = new SimpleStringProperty("Store 1");
        position = new SimpleStringProperty("Manager");
        createItem = new SimpleBooleanProperty(true);
        modifyItem = new SimpleBooleanProperty(false);
        viewSale = new SimpleBooleanProperty(true);
        createSale = new SimpleBooleanProperty(false);
        modifySale = new SimpleBooleanProperty(true);
        viewCustomer = new SimpleBooleanProperty(false);
        createCustomer = new SimpleBooleanProperty(true);
        modifyCustomer = new SimpleBooleanProperty(false);
        manageSupplier = new SimpleBooleanProperty(true);
        manageEmployee = new SimpleBooleanProperty(false);
    }

    public EmployeeTable(@NotNull EmployeeTable item) {
        employeeId = new SimpleIntegerProperty(item.employeeId.get());
        displayName = new SimpleStringProperty(item.displayName.get());
        firstName = new SimpleStringProperty(item.firstName.get());
        contact = new SimpleStringProperty(item.contact.get());
        store = new SimpleStringProperty(item.store.get());
        position = new SimpleStringProperty(item.position.get());
        createItem = new SimpleBooleanProperty(item.createItem.get());
        modifyItem = new SimpleBooleanProperty(item.modifyItem.get());
        viewSale = new SimpleBooleanProperty(item.viewSale.get());
        createSale = new SimpleBooleanProperty(item.createSale.get());
        modifySale = new SimpleBooleanProperty(item.modifySale.get());
        viewCustomer = new SimpleBooleanProperty(item.viewCustomer.get());
        createCustomer = new SimpleBooleanProperty(item.createCustomer.get());
        modifyCustomer = new SimpleBooleanProperty(item.modifyCustomer.get());
        manageSupplier = new SimpleBooleanProperty(item.manageSupplier.get());
        manageEmployee = new SimpleBooleanProperty(item.manageEmployee.get());
    }

    public void set(@NotNull EmployeeTable item) {
        displayName.set(item.displayName.get());
        firstName.set(item.firstName.get());
        contact.set(item.contact.get());
        store.set(item.store.get());
        position.set(item.position.get());
        createItem.set(item.createItem.get());
        modifyItem.set(item.modifyItem.get());
        viewSale.set(item.viewSale.get());
        createSale.set(item.createSale.get());
        modifySale.set(item.modifySale.get());
        viewCustomer.set(item.viewCustomer.get());
        createCustomer.set(item.createCustomer.get());
        modifyCustomer.set(item.modifyCustomer.get());
        manageSupplier.set(item.manageSupplier.get());
        manageEmployee.set(item.manageEmployee.get());
    }

    public EmployeePermissions getPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(createItem.get());
        permissions.setModifyItem(modifyItem.get());
        permissions.setViewSale(viewSale.get());
        permissions.setCreateSale(createSale.get());
        permissions.setModifySale(modifySale.get());
        permissions.setViewCustomer(viewCustomer.get());
        permissions.setCreateCustomer(createCustomer.get());
        permissions.setModifyCustomer(modifyCustomer.get());
        permissions.setManageSupplier(manageSupplier.get());
        permissions.setManageEmployee(manageEmployee.get());
        return permissions;
    }

    public void setPermissions(@NotNull EmployeePermissions permissions) {
        createItem.set(permissions.isCreateItem());
        modifyItem.set(permissions.isModifyItem());
        viewSale.set(permissions.isViewSale());
        createSale.set(permissions.isCreateSale());
        modifySale.set(permissions.isModifySale());
        viewCustomer.set(permissions.isViewCustomer());
        createCustomer.set(permissions.isCreateCustomer());
        modifyCustomer.set(permissions.isModifyCustomer());
        manageSupplier.set(permissions.getManageSupplier());
        manageEmployee.set(permissions.getManageEmployee());
    }

    public String getDisplayName() {
        return displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public StringProperty displayNameProperty() {
        return displayName;
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

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public String getStore() {
        return store.get();
    }

    public void setStore(String store) {
        this.store.set(store);
    }

    public StringProperty storeProperty() {
        return store;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public boolean isCreateItem() {
        return createItem.get();
    }

    public void setCreateItem(boolean createItem) {
        this.createItem.set(createItem);
    }

    public BooleanProperty createItemProperty() {
        return createItem;
    }

    public boolean isModifyItem() {
        return modifyItem.get();
    }

    public void setModifyItem(boolean modifyItem) {
        this.modifyItem.set(modifyItem);
    }

    public BooleanProperty modifyItemProperty() {
        return modifyItem;
    }

    public boolean isViewSale() {
        return viewSale.get();
    }

    public void setViewSale(boolean viewSale) {
        this.viewSale.set(viewSale);
    }

    public BooleanProperty viewSaleProperty() {
        return viewSale;
    }

    public boolean isCreateSale() {
        return createSale.get();
    }

    public void setCreateSale(boolean createSale) {
        this.createSale.set(createSale);
    }

    public BooleanProperty createSaleProperty() {
        return createSale;
    }

    public boolean isModifySale() {
        return modifySale.get();
    }

    public void setModifySale(boolean modifySale) {
        this.modifySale.set(modifySale);
    }

    public BooleanProperty modifySaleProperty() {
        return modifySale;
    }

    public boolean isViewCustomer() {
        return viewCustomer.get();
    }

    public void setViewCustomer(boolean viewCustomer) {
        this.viewCustomer.set(viewCustomer);
    }

    public BooleanProperty viewCustomerProperty() {
        return viewCustomer;
    }

    public boolean isCreateCustomer() {
        return createCustomer.get();
    }

    public void setCreateCustomer(boolean createCustomer) {
        this.createCustomer.set(createCustomer);
    }

    public BooleanProperty createCustomerProperty() {
        return createCustomer;
    }

    public boolean isModifyCustomer() {
        return modifyCustomer.get();
    }

    public void setModifyCustomer(boolean modifyCustomer) {
        this.modifyCustomer.set(modifyCustomer);
    }

    public BooleanProperty modifyCustomerProperty() {
        return modifyCustomer;
    }

    public boolean getManageSupplier() {
        return manageSupplier.get();
    }

    public void setManageSupplier(boolean manageSupplier) {
        this.manageSupplier.set(manageSupplier);
    }

    public BooleanProperty manageSupplierProperty() {
        return manageSupplier;
    }

    public boolean getManageEmployee() {
        return manageEmployee.get();
    }

    public void setManageEmployee(boolean manageEmployee) {
        this.manageEmployee.set(manageEmployee);
    }

    public BooleanProperty manageEmployeeProperty() {
        return manageEmployee;
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public ReadOnlyIntegerProperty employeeIdProperty() {
        return employeeId;
    }
}
