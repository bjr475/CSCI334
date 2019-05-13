package com.app.main.model;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigInteger;

public class CatalogueModel {
    private String itemID;
    private StringProperty itemName;
    private StringProperty modelType;
    private StringProperty subjectArea;
    private StringProperty retailPrice;
    private StringProperty dateFirstStocked;
    private MapProperty<ShopModel, Integer> storeStock;
    private StringProperty description;
    private MapProperty<SupplierModel, BigInteger> supplierItems;

    public CatalogueModel() {
        itemID = "UNKNOWN";
        itemName = new SimpleStringProperty();
        modelType = new SimpleStringProperty("Model Type");
        subjectArea = new SimpleStringProperty("Subject Area");
        retailPrice = new SimpleStringProperty();
        dateFirstStocked = new SimpleStringProperty();
        //storeStock
        description = new SimpleStringProperty();
        //supplierItems
    }

    public void reset() {
        //itemID.setValue(generateID());
        itemName.setValue("");
        modelType.setValue("Model Type");
        subjectArea.setValue("Subject Area");
        retailPrice.setValue("");
        dateFirstStocked.setValue("");
        description.setValue("");
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String userID) {
        this.itemID = itemID;
    }

    public String getitemName() {
        return itemName.get();
    }

    public void setitemName(String itemName) {
        this.itemName.set(itemName);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public String getmodelType() {
        return modelType.get();
    }

    public void setmodelType(String modelType) {
        this.modelType.set(modelType);
    }

    public StringProperty modelTypeProperty() {
        return modelType;
    }

    public String getsubjectArea() {
        return subjectArea.get();
    }

    public void setsubjectArea(String subjectArea) {
        this.subjectArea.set(subjectArea);
    }

    public StringProperty subjectAreaProperty() {
        return subjectArea;
    }

    public String getretailPrice() {
        return retailPrice.get();
    }

    public void setretailPrice(String retailPrice) {
        this.retailPrice.set(retailPrice);
    }

    public StringProperty retailPriceProperty() {
        return retailPrice;
    }

}
