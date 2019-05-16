package com.app.main.model.catalogue;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CatalogueItemLocationModel {
    private StringProperty store;
    private IntegerProperty count;

    public CatalogueItemLocationModel() {
        store = new SimpleStringProperty("Store Name");
        count = new SimpleIntegerProperty(0);
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

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public IntegerProperty countProperty() {
        return count;
    }
}
