package com.app.main.model.store;

import com.app.main.model.AddressModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

public class StoreModel {
    private ReadOnlyIntegerProperty storeId;
    private StringProperty name;
    private ObjectProperty<AddressModel> address;
    private IntegerProperty managerId;
    private StringProperty managerName;

    public StoreModel(int storeId) {
        this.storeId = new SimpleIntegerProperty(storeId);
        name = new SimpleStringProperty(String.format("Store - %d", storeId));
        address = new SimpleObjectProperty<>(new AddressModel(String.format("%d Store St", storeId), "Stores", "NSW", "2830"));
        managerId = new SimpleIntegerProperty(1);
        managerName = new SimpleStringProperty("Employee 1");
    }

    public StoreModel(@NotNull StoreModel item) {
        storeId = new SimpleIntegerProperty(item.storeId.get());
        name = new SimpleStringProperty(item.name.get());
        address = new SimpleObjectProperty<>(item.address.get());
        managerId = new SimpleIntegerProperty(item.managerId.get());
        managerName = new SimpleStringProperty(item.managerName.get());
    }

    public void set(@NotNull StoreModel item) {
        name.set(item.name.get());
        address.set(item.address.get());
        managerId.set(item.managerId.get());
        managerName.set(item.managerName.get());
    }

    public int getStoreId() {
        return storeId.get();
    }

    public ReadOnlyIntegerProperty storeIdProperty() {
        return storeId;
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

    public int getManagerId() {
        return managerId.get();
    }

    public void setManagerId(int managerId) {
        this.managerId.set(managerId);
    }

    public IntegerProperty managerIdProperty() {
        return managerId;
    }

    public String getManagerName() {
        return managerName.get();
    }

    public void setManagerName(String managerName) {
        this.managerName.set(managerName);
    }

    public StringProperty managerNameProperty() {
        return managerName;
    }
}
