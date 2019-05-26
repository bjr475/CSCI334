package com.app.main.controller.employee.supplier;

import com.app.database.Database;
import com.app.main.controller.AViewController;
import com.app.main.controller.employee.SearchableComboBoxUtil;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.store.StoreModel;
import com.app.main.model.supplier.SupplierCatalogueItemModel;
import com.app.main.model.supplier.SupplierItemModel;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SupplierItemViewController extends AViewController {
    private static final Logger logger = LogManager.getLogger(SupplierItemViewController.class.getName());

    public JFXDialog dialog;
    public TextField itemName;
    public ChoiceBox<String> itemType;
    public ChoiceBox<String> itemSubject;
    public Spinner<Double> retailPrice;
    public Spinner<Double> supplierPrice;
    public TextArea itemDescription;
    public TableView<CatalogueItemLocationModel> itemStores;
    public ComboBox<String> itemStoreNames;
    public Spinner<Integer> itemStoreQuantity;

    private SupplierCatalogueItemModel catalogueItemModel;
    private ConfirmItemCallback confirmItemCallback;

    public SupplierItemViewController(ApplicationModel model) {
        super(model);
        catalogueItemModel = null;
        confirmItemCallback = null;
    }

    @FXML
    public void initialize() {
        {
            TableColumn<CatalogueItemLocationModel, String> storeColumn = new TableColumn<>("Store");
            storeColumn.setCellValueFactory(param -> param.getValue().storeProperty());
            itemStores.getColumns().add(storeColumn);

            TableColumn<CatalogueItemLocationModel, Number> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(param -> param.getValue().countProperty());
            itemStores.getColumns().add(quantityColumn);
        }
        SearchableComboBoxUtil.createSearchableComboBox(itemStoreNames, (input, object) -> object.contains(input));
        setCatalogueItemModel(new SupplierCatalogueItemModel(
                new CatalogueItemModel(0),
                new SupplierItemModel()
        ));
    }

    private void refreshStoresTable() {
        if (catalogueItemModel != null) {
            itemStores.setItems(FXCollections.observableArrayList(catalogueItemModel.getCatalogueItem().getStores()));
        } else {
            itemStores.setItems(FXCollections.observableArrayList());
        }
        itemStores.refresh();
    }

    public void open(StackPane parent, ConfirmItemCallback callback) {
        logger.info("Open Supplier Item View");
        itemStoreNames.itemsProperty().get().clear();
        for (StoreModel store : Database.INSTANCE.getStore().getStores()) {
            itemStoreNames.itemsProperty().get().add(store.getName());
        }
        dialog.show(parent);
        confirmItemCallback = callback;
    }

    @FXML
    private void addItemStore() {
        String store = SearchableComboBoxUtil.getComboBoxValue(itemStoreNames);
        Integer quantity = itemStoreQuantity.getValue();
        if (store != null && quantity != null) {
            CatalogueItemLocationModel model = new CatalogueItemLocationModel();
            model.setStore(store);
            model.setCount(quantity);
            catalogueItemModel.getCatalogueItem().getStores().add(model);
            refreshStoresTable();
        }
    }

    @FXML
    private void cancel() {
        setCatalogueItemModel(new SupplierCatalogueItemModel(
                new CatalogueItemModel(0),
                new SupplierItemModel()
        ));
        dialog.close();
    }

    @FXML
    private void confirm() {
        catalogueItemModel.getCatalogueItem().setPrice(retailPrice.getValue());
        catalogueItemModel.getSupplierItem().setPrice(supplierPrice.getValue());
        catalogueItemModel.getCatalogueItem().setName(itemName.getText());
        catalogueItemModel.getCatalogueItem().setType(itemType.getValue());
        catalogueItemModel.getCatalogueItem().setSubject(itemSubject.getValue());
        catalogueItemModel.getCatalogueItem().setPrice(retailPrice.getValue());
        catalogueItemModel.getSupplierItem().setPrice(supplierPrice.getValue());
        catalogueItemModel.getCatalogueItem().setDescription(itemDescription.getText());
        logger.info("On Confirm Item: {}", catalogueItemModel);
        confirmItemCallback.confirm(catalogueItemModel);
        cancel();
    }

    public SupplierCatalogueItemModel getCatalogueItemModel() {
        return catalogueItemModel;
    }

    public void setCatalogueItemModel(SupplierCatalogueItemModel catalogueItemModel) {
        this.catalogueItemModel = catalogueItemModel;
        refreshStoresTable();
        SearchableComboBoxUtil.clearComboBoxValue(itemStoreNames);
        itemStoreQuantity.getValueFactory().setValue(0);
        if (catalogueItemModel != null) {
            itemName.setText(catalogueItemModel.getCatalogueItem().getName());
            itemType.setValue(catalogueItemModel.getCatalogueItem().getType());
            itemSubject.setValue(catalogueItemModel.getCatalogueItem().getSubject());
            retailPrice.getValueFactory().setValue(catalogueItemModel.getCatalogueItem().getPrice());
            supplierPrice.getValueFactory().setValue(catalogueItemModel.getSupplierItem().getPrice());
            itemDescription.setText(catalogueItemModel.getCatalogueItem().getDescription());
        } else {
            itemName.setText("");
            itemType.setValue("Type");
            itemSubject.setValue("Subject");
            retailPrice.getValueFactory().setValue(0.);
            supplierPrice.getValueFactory().setValue(0.);
            itemDescription.setText("");
        }
    }

    public interface ConfirmItemCallback {
        void confirm(SupplierCatalogueItemModel item);
    }
}
