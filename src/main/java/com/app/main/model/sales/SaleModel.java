package com.app.main.model.sales;

import com.app.main.model.CustomerModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class SaleModel {
    private ReadOnlyIntegerProperty id;
    private DoubleProperty total;
    private ListProperty<SaleItemModel> items;
    private ObjectProperty<CustomerModel> customer;
    private ObjectProperty<Date> transactionDate;

    public SaleModel(int id) {
        this.id = new SimpleIntegerProperty(id);
        total = new SimpleDoubleProperty(0.);
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        customer = new SimpleObjectProperty<>(null);
        transactionDate = new SimpleObjectProperty<>(null);
    }

    public SaleModel(@NotNull SaleModel sale) {
        id = new SimpleIntegerProperty(sale.id.get());
        total = new SimpleDoubleProperty(sale.total.get());
        items = new SimpleListProperty<>(sale.items.get());
        customer = new SimpleObjectProperty<>(sale.customer.get());
        transactionDate = new SimpleObjectProperty<>(sale.transactionDate.get());
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public ObservableList<SaleItemModel> getItems() {
        return items.get();
    }

    public void setItems(ObservableList<SaleItemModel> items) {
        this.items.set(items);
    }

    public ListProperty<SaleItemModel> itemsProperty() {
        return items;
    }

    public CustomerModel getCustomer() {
        return customer.get();
    }

    public void setCustomer(CustomerModel customer) {
        this.customer.set(customer);
    }

    public ObjectProperty<CustomerModel> customerProperty() {
        return customer;
    }

    public Date getTransactionDate() {
        return transactionDate.get();
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate.set(transactionDate);
    }

    public ObjectProperty<Date> transactionDateProperty() {
        return transactionDate;
    }
}
