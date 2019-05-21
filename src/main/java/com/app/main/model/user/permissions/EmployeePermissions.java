package com.app.main.model.user.permissions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

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
    private BooleanProperty createSupplier;
    private BooleanProperty modifySupplier;

    public EmployeePermissions() {
        createItem = new SimpleBooleanProperty(false);
        modifyItem = new SimpleBooleanProperty(false);
        viewSale = new SimpleBooleanProperty(false);
        createSale = new SimpleBooleanProperty(false);
        modifySale = new SimpleBooleanProperty(false);
        viewCustomer = new SimpleBooleanProperty(false);
        createCustomer = new SimpleBooleanProperty(false);
        modifyCustomer = new SimpleBooleanProperty(false);
        createSupplier = new SimpleBooleanProperty(false);
        modifySupplier = new SimpleBooleanProperty(false);
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

    public boolean isCreateSupplier() {
        return createSupplier.get();
    }

    public void setCreateSupplier(boolean createSupplier) {
        this.createSupplier.set(createSupplier);
    }

    public BooleanProperty createSupplierProperty() {
        return createSupplier;
    }

    public boolean isModifySupplier() {
        return modifySupplier.get();
    }

    public void setModifySupplier(boolean modifySupplier) {
        this.modifySupplier.set(modifySupplier);
    }

    public BooleanProperty modifySupplierProperty() {
        return modifySupplier;
    }
}
