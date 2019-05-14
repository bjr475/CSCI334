package com.app.main.model;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigInteger;
import java.util.Date;

public class CatalogueModel {
    private String itemID;
    private StringProperty itemName;
    private StringProperty modelType;
    private StringProperty subjectArea;
    private StringProperty retailPrice;
    private Date dateFirstStocked;
    private MapProperty<ShopModel, Integer> storeStock;
    private StringProperty description;
    private MapProperty<SupplierModel, BigInteger> supplierItems;

    public CatalogueModel() {
        itemName = new SimpleStringProperty();
        modelType = new SimpleStringProperty("Model Type");
        subjectArea = new SimpleStringProperty("Subject Area");
        retailPrice = new SimpleStringProperty();
        dateFirstStocked = new Date();
        storeStock = new SimpleMapProperty<>();
        description = new SimpleStringProperty();
        supplierItems = new SimpleMapProperty<>();
    }

    public CatalogueModel(String itemID,
                          String name,
                          String model,
                          String subject,
                          String price,
                          Date date,
                          MapProperty<ShopModel, Integer> storeStock,
                          String description,
                          MapProperty<SupplierModel, BigInteger> supplierItems) {
        this.itemID = itemID;
        this.itemName = new SimpleStringProperty(name);
        this.subjectArea = new SimpleStringProperty(model);
        this.subjectArea = new SimpleStringProperty(subject);
        this.retailPrice = new SimpleStringProperty(price);
        this.dateFirstStocked = date;
        /*this.storeStock = new MapProperty<ShopModel, Integer>;
        this.storeStock = storeStock;
        this.description = new SimpleStringProperty(description);
        this.supplierItems = new MapProperty<SupplierModel, BigInteger>;
        this.supplierItems = supplierItems;*/
    }

    public CatalogueModel(CatalogueModel model) {
        this.itemID = model.itemID;
        this.itemName = new SimpleStringProperty(model.itemName.get());
        this.subjectArea = new SimpleStringProperty(model.subjectArea.get());
        this.retailPrice = new SimpleStringProperty(model.retailPrice.get());
        this.dateFirstStocked = model.dateFirstStocked;
        this.storeStock = new SimpleMapProperty<>(model.storeStock.get());
        this.description = new SimpleStringProperty(model.description.get());
        this.supplierItems = new SimpleMapProperty<>(model.supplierItems.get());
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public String getModelType() {
        return modelType.get();
    }

    public void setModelType(String modelType) {
        this.modelType.set(modelType);
    }

    public StringProperty modelTypeProperty() {
        return modelType;
    }

    public String getSubjectArea() {
        return subjectArea.get();
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea.set(subjectArea);
    }

    public StringProperty subjectAreaProperty() {
        return subjectArea;
    }

    public String getRetailPrice() {
        return retailPrice.get();
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice.set(retailPrice);
    }

    public StringProperty retailPriceProperty() {
        return retailPrice;
    }

    public Date getDateFirstStocked() {
        return dateFirstStocked;
    }

    public void setDateFirstStocked(Date dateFirstStocked) {
        this.dateFirstStocked = dateFirstStocked;
    }

    public MapProperty<ShopModel, Integer> getstoreStock() {
        return storeStock;
    }

    public void setStoreStock(ShopModel shop, Integer quantity) {
        this.storeStock.put(shop, quantity);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public MapProperty<SupplierModel, BigInteger> getSupplierItems() {
        return supplierItems;
    }

    public void setSupplierItems(SupplierModel supplier, BigInteger price) {
        this.supplierItems.put(supplier, price);
    }
}
