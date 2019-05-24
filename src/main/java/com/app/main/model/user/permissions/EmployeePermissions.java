package com.app.main.model.user.permissions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class EmployeePermissions implements Serializable {
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

    public EmployeePermissions() {
        createItem = new SimpleBooleanProperty(false);
        modifyItem = new SimpleBooleanProperty(false);
        viewSale = new SimpleBooleanProperty(false);
        createSale = new SimpleBooleanProperty(false);
        modifySale = new SimpleBooleanProperty(false);
        viewCustomer = new SimpleBooleanProperty(false);
        createCustomer = new SimpleBooleanProperty(false);
        modifyCustomer = new SimpleBooleanProperty(false);
        manageSupplier = new SimpleBooleanProperty(false);
        manageEmployee = new SimpleBooleanProperty(false);
    }

    @SuppressWarnings("Duplicates")
    public static EmployeePermissions newManagerPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(true);
        permissions.setModifyItem(true);
        permissions.setViewSale(true);
        permissions.setCreateSale(true);
        permissions.setModifySale(true);
        permissions.setViewCustomer(true);
        permissions.setCreateCustomer(true);
        permissions.setModifyCustomer(true);
        permissions.setManageSupplier(false);
        permissions.setManageEmployee(false);
        return permissions;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeePermissions newSalesAssitantPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(false);
        permissions.setModifyItem(false);
        permissions.setViewSale(true);
        permissions.setCreateSale(true);
        permissions.setModifySale(true);
        permissions.setViewCustomer(true);
        permissions.setCreateCustomer(true);
        permissions.setModifyCustomer(true);
        permissions.setManageSupplier(false);
        permissions.setManageEmployee(false);
        return permissions;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeePermissions newStockAssistantPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(true);
        permissions.setModifyItem(true);
        permissions.setViewSale(false);
        permissions.setCreateSale(false);
        permissions.setModifySale(false);
        permissions.setViewCustomer(false);
        permissions.setCreateCustomer(false);
        permissions.setModifyCustomer(false);
        permissions.setManageSupplier(false);
        permissions.setManageEmployee(false);
        return permissions;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeePermissions newDefaultPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(false);
        permissions.setModifyItem(false);
        permissions.setViewSale(false);
        permissions.setCreateSale(false);
        permissions.setModifySale(false);
        permissions.setViewCustomer(false);
        permissions.setCreateCustomer(false);
        permissions.setModifyCustomer(false);
        permissions.setManageSupplier(false);
        permissions.setManageEmployee(false);
        return permissions;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeePermissions newAdminPermissions() {
        EmployeePermissions permissions = new EmployeePermissions();
        permissions.setCreateItem(true);
        permissions.setModifyItem(true);
        permissions.setViewSale(true);
        permissions.setCreateSale(true);
        permissions.setModifySale(true);
        permissions.setViewCustomer(true);
        permissions.setCreateCustomer(true);
        permissions.setModifyCustomer(true);
        permissions.setManageSupplier(true);
        permissions.setManageEmployee(true);
        return permissions;
    }

    private void writeObject(@NotNull ObjectOutputStream out) throws IOException {
        out.writeBoolean(createItem.get());
        out.writeBoolean(modifyItem.get());
        out.writeBoolean(viewSale.get());
        out.writeBoolean(createSale.get());
        out.writeBoolean(modifySale.get());
        out.writeBoolean(viewCustomer.get());
        out.writeBoolean(createCustomer.get());
        out.writeBoolean(modifyCustomer.get());
        out.writeBoolean(manageSupplier.get());
        out.writeBoolean(manageEmployee.get());
    }

    private void readObject(@NotNull ObjectInputStream in) throws IOException, ClassNotFoundException {
        createItem = new SimpleBooleanProperty(in.readBoolean());
        modifyItem = new SimpleBooleanProperty(in.readBoolean());
        viewSale = new SimpleBooleanProperty(in.readBoolean());
        createSale = new SimpleBooleanProperty(in.readBoolean());
        modifySale = new SimpleBooleanProperty(in.readBoolean());
        viewCustomer = new SimpleBooleanProperty(in.readBoolean());
        createCustomer = new SimpleBooleanProperty(in.readBoolean());
        modifyCustomer = new SimpleBooleanProperty(in.readBoolean());
        manageSupplier = new SimpleBooleanProperty(in.readBoolean());
        manageEmployee = new SimpleBooleanProperty(in.readBoolean());
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
}
