package com.app.main.controller.employee.sales;

import com.app.database.Database;
import com.app.main.controller.employee.SearchableComboBoxUtil;
import com.app.main.model.ApplicationModel;
import com.app.main.model.CustomerModel;
import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import com.app.main.model.sales.SaleItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewSaleItemsViewController extends AChildSalesViewController {
    private static final Logger logger = LogManager.getLogger(NewSaleItemsViewController.class.getName());

    public TableView<SaleItemModel> itemsTable;
    public ComboBox<CatalogueItemIdNameModel> itemSearch;
    public Spinner<Integer> itemQuantity;
    public Spinner<Double> itemDiscount;
    public ComboBox<CustomerModel> customerSearch;

    private ObservableList<SaleItemModel> items;

    public NewSaleItemsViewController(ApplicationModel model) {
        super(model);

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                itemSearch.setItems(FXCollections.observableArrayList(Database.INSTANCE.getModel().getIdNameModels()));
                customerSearch.setItems(FXCollections.observableArrayList(Database.INSTANCE.getCustomer().getCustomers()));
                CustomerModel newCustomer = new CustomerModel();
                newCustomer.setId(0);
                newCustomer.setFirstName("New");
                newCustomer.setLastName("Customer");
                customerSearch.getItems().add(0, newCustomer);
            }
        });

        items = FXCollections.observableArrayList();
        items.addListener((ListChangeListener<SaleItemModel>) c -> {
            itemsTable.setItems(items);
            itemsTable.refresh();
        });
    }
    
    public void initialize() {
        SalesUtil.buildItemsTable(itemsTable);
        SearchableComboBoxUtil.setCatalogueIdModelConverter(itemSearch);
        SearchableComboBoxUtil.createSearchableComboBox(itemSearch, SearchableComboBoxUtil.CATALOGUE_COMPARATOR);

        SearchableComboBoxUtil.setCustomerModelConverter(customerSearch);
        SearchableComboBoxUtil.createSearchableComboBox(customerSearch, SearchableComboBoxUtil.CUSTOMER_COMPARATOR);
    }

    public void addItem() {
        CatalogueItemIdNameModel item = SearchableComboBoxUtil.getComboBoxValue(itemSearch);
        Integer quantity = itemQuantity.getValue();
        Double discount = itemDiscount.getValue();
        if (item != null && quantity != null && discount != null) {
            SaleItemModel saleItem = new SaleItemModel();
            saleItem.setDiscount(discount);
            saleItem.setQuantity(quantity);
            saleItem.setItem(item);
            items.add(saleItem);
            logger.info("Add Sale Item: {} = {}", saleItem, item);

            SearchableComboBoxUtil.clearComboBoxValue(itemSearch);
            itemQuantity.getValueFactory().setValue(0);
            itemDiscount.getValueFactory().setValue(0.);
        }
    }

    public void cancel() {
        getOwner().newSaleCancel();
    }

    public void next() {
        getOwner().newSaleItemsNext();
    }

    public ObservableList<SaleItemModel> getItems() {
        return items;
    }

    void clearItems() {
        items.clear();
        itemsTable.setItems(items);
        itemsTable.refresh();
    }

    CustomerModel getSelectedCustomer() {
        return SearchableComboBoxUtil.getComboBoxValue(customerSearch);
    }
}
