package com.app.main.controller.employee.search;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.controller.employee.AChildEmployeeViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.customer.CustomerModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class SearchViewController extends AChildEmployeeViewController {
    public StackPane parent;
    public JFXDialog searchDialog;
    public TableView<CatalogueItemModel> searchItems;
    public TableView<CustomerModel> searchCustomers;
    public TextField searchWords;
    public JFXCheckBox searchInItems;
    public JFXCheckBox searchInCustomers;

    public SearchViewController(ApplicationModel model) {
        super(model);
    }

    public void initialize() {
        buildSearchItems();
        buildSearchCustomers();
    }

    private void buildSearchCustomers() {
        TableColumn<CustomerModel, Number> customerId = new TableColumn<>("Customer Id");
        customerId.setCellValueFactory(param -> param.getValue().idProperty());
        customerId.setCellFactory(Util::getIdCell);
        searchCustomers.getColumns().add(customerId);

        TableColumn<CustomerModel, String> customerFirst = new TableColumn<>("First Name");
        customerFirst.setCellValueFactory(param -> param.getValue().firstNameProperty());
        searchCustomers.getColumns().add(customerFirst);

        TableColumn<CustomerModel, String> customerLast = new TableColumn<>("Last Name");
        customerLast.setCellValueFactory(param -> param.getValue().lastNameProperty());
        searchCustomers.getColumns().add(customerLast);

        TableColumn<CustomerModel, String> customerDisplay = new TableColumn<>("Email");
        customerDisplay.setCellValueFactory(param -> param.getValue().emailProperty());
        searchCustomers.getColumns().add(customerDisplay);

        searchCustomers.setRowFactory(param -> {
            TableRow<CustomerModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) getOwner().gotoCustomer(row.getItem());
            });
            return row;
        });
    }

    private void buildSearchItems() {
        TableColumn<CatalogueItemModel, Number> itemId = new TableColumn<>("Item No.");
        itemId.setCellValueFactory(param -> param.getValue().itemIdProperty());
        itemId.setCellFactory(Util::getIdCell);
        searchItems.getColumns().add(itemId);

        TableColumn<CatalogueItemModel, String> itemName = new TableColumn<>("Name");
        itemName.setCellValueFactory(param -> param.getValue().nameProperty());
        searchItems.getColumns().add(itemName);

        TableColumn<CatalogueItemModel, Number> itemPrice = new TableColumn<>("Price");
        itemPrice.setCellValueFactory(param -> param.getValue().priceProperty());
        itemPrice.setCellFactory(Util::getPriceCell);
        searchItems.getColumns().add(itemPrice);

        searchItems.setRowFactory(param -> {
            TableRow<CatalogueItemModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) getOwner().gotoCatalogue(row.getItem());
            });
            return row;
        });
    }

    public void search() {
        searchDialog.show(parent);
    }

    public void performSearch() {
        searchItems.getItems().clear();
        searchCustomers.getItems().clear();

        searchDialog.close();
        String keywords = searchWords.getText();
        boolean inItems = searchInItems.isSelected();
        boolean inCustomer = searchInItems.isSelected();

        if (inItems) {
            searchItems.setItems(FXCollections.observableArrayList(Database.INSTANCE.getModel().searchModels(keywords)));
        }
        searchItems.refresh();

        if (inCustomer) {
            searchCustomers.setItems(FXCollections.observableArrayList(Database.INSTANCE.getCustomer().searchCustomers(keywords)));
        }
        searchCustomers.refresh();

        searchWords.setText("");
        searchInItems.setSelected(true);
        searchInCustomers.setSelected(true);
    }
}
