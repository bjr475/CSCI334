package com.app.main.model.catalogue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

public class CatalogueItemModel {
    private IntegerProperty itemId;
    private StringProperty name;
    private StringProperty type;
    private DoubleProperty price;
    private StringProperty subject;
    private StringProperty description;
    private ObjectProperty<Date> stockedOn;
    private ObjectProperty<ObservableList<CatalogueItemLocationModel>> stores;
    private ObjectProperty<ObservableList<CatalogueItemSupplierModel>> suppliers;

    private IntegerProperty stockTotal;

    public CatalogueItemModel(int itemId) {
        this.itemId = new SimpleIntegerProperty(itemId);
        name = new SimpleStringProperty("Name");
        type = new SimpleStringProperty("Type");
        price = new SimpleDoubleProperty(19.95);
        subject = new SimpleStringProperty("Subject");
        description = new SimpleStringProperty("Description");
        stockedOn = new SimpleObjectProperty<>(Date.from(Instant.now()));
        stores = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        suppliers = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        stockTotal = new SimpleIntegerProperty(0);
        addListeners();
    }

    public CatalogueItemModel(@NotNull CatalogueItemModel item) {
        this.itemId = new SimpleIntegerProperty(item.itemId.get());
        name = new SimpleStringProperty(item.name.get());
        type = new SimpleStringProperty(item.type.get());
        price = new SimpleDoubleProperty(item.price.get());
        subject = new SimpleStringProperty(item.subject.get());
        description = new SimpleStringProperty(item.description.get());
        stockedOn = new SimpleObjectProperty<>(item.stockedOn.get());
        stores = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        item.stores.get().forEach(i -> stores.get().add(new CatalogueItemLocationModel(i)));
        suppliers = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        item.suppliers.get().forEach(i -> suppliers.get().add(new CatalogueItemSupplierModel(i)));
        stockTotal = new SimpleIntegerProperty(0);
        addListeners();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        CatalogueItemModel mod = obj instanceof CatalogueItemModel ? ((CatalogueItemModel) obj) : null;
        if (mod != null) {
            return mod.getItemId() == itemId.get();
        }
        return super.equals(obj);
    }

    private void addListeners() {
        stores.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) oldValue.removeListener(this::onStoresChanged);
            if (newValue != null) {
                updateStockCount(newValue);
                newValue.addListener(this::onStoresChanged);
            }
        });
        stores.get().addListener(this::onStoresChanged);
    }

    private void onStoresChanged(@NotNull ListChangeListener.Change<? extends CatalogueItemLocationModel> change) {
        updateStockCount(change.getList());
    }

    private void updateStockCount(@NotNull ObservableList<? extends CatalogueItemLocationModel> items) {
        int total = 0;
        for (CatalogueItemLocationModel item : items) total += item.getCount();
        stockTotal.set(total);
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

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Date getStockedOn() {
        return stockedOn.get();
    }

    public void setStockedOn(Date stockedOn) {
        this.stockedOn.set(stockedOn);
    }

    public ObjectProperty<Date> stockedOnProperty() {
        return stockedOn;
    }

    public ObservableList<CatalogueItemLocationModel> getStores() {
        return stores.get();
    }

    public void setStores(ObservableList<CatalogueItemLocationModel> stores) {
        this.stores.set(stores);
    }

    public ObjectProperty<ObservableList<CatalogueItemLocationModel>> storesProperty() {
        return stores;
    }

    public ObservableList<CatalogueItemSupplierModel> getSuppliers() {
        return suppliers.get();
    }

    public void setSuppliers(ObservableList<CatalogueItemSupplierModel> suppliers) {
        this.suppliers.set(suppliers);
    }

    public ObjectProperty<ObservableList<CatalogueItemSupplierModel>> suppliersProperty() {
        return suppliers;
    }

    public int getStockTotal() {
        return stockTotal.get();
    }

    public IntegerProperty stockTotalProperty() {
        return stockTotal;
    }

    public void set(@NotNull CatalogueItemModel model) {
        this.itemId = new SimpleIntegerProperty(model.itemId.get());
        name = model.name;
        type = model.type;
        price = model.price;
        subject = model.subject;
        description = model.description;
        stockedOn = model.stockedOn;
        stores = model.stores;
        suppliers = model.suppliers;
    }

    public int getItemId() {
        return itemId.get();
    }

    public IntegerProperty itemIdProperty() {
        return itemId;
    }
}
