package com.app.main.controller.employee;

import com.app.main.Util;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CatalogueViewController extends AChildEmployeeViewController implements IEditorActionItem {
    private static final Logger logger = LogManager.getLogger(CatalogueViewController.class.getName());

    /* Tool Drawer */
    public JFXDrawer toolDrawer;

    /* Search Control */
    public ScrollPane searchMenu;

    /* Filter Control */
    public ScrollPane filterMenu;

    /* Add Control */
    public ScrollPane addMenu;

    /* Edit Control */
    public ScrollPane editMenu;
    public TextField editItemName;
    public Label editCurrentSupplier;

    /* Catalogue Table */
    public TableView<CatalogueItemModel> catalogueTable;

    public CatalogueViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        ArrayList<CatalogueItemModel> models = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CatalogueItemModel catalogueItem = new CatalogueItemModel(String.format("%d", i));
            for (int j = 0; j < 5; j++) {
                CatalogueItemLocationModel location = new CatalogueItemLocationModel();
                location.setStore(String.format("Store %d", j));
                location.setCount(j);
                catalogueItem.getStores().add(location);

                CatalogueItemSupplierModel supplier = new CatalogueItemSupplierModel();
                supplier.setName(String.format("Supplier %d", i));
                supplier.setCurrent(j == 3);
                catalogueItem.getSuppliers().add(supplier);
            }
            models.add(catalogueItem);
        }

        catalogueTable.setItems(FXCollections.observableArrayList(models));
        catalogueTable.refresh();

        {
            TableColumn<CatalogueItemModel, String> itemIdColumn = new TableColumn<>("Item No");
            itemIdColumn.setCellValueFactory(p -> Bindings.concat(p.getValue().getItemId()));
            catalogueTable.getColumns().add(itemIdColumn);

            TableColumn<CatalogueItemModel, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(p -> p.getValue().nameProperty());
            catalogueTable.getColumns().add(nameColumn);

            TableColumn<CatalogueItemModel, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(p -> p.getValue().typeProperty());
            catalogueTable.getColumns().add(typeColumn);

            TableColumn<CatalogueItemModel, String> subjectColumn = new TableColumn<>("Subject Area");
            subjectColumn.setCellValueFactory(p -> p.getValue().subjectProperty());
            catalogueTable.getColumns().add(subjectColumn);

            TableColumn<CatalogueItemModel, String> retailPriceColumn = new TableColumn<>("Retail Price");
            retailPriceColumn.setCellValueFactory(p -> Bindings.concat(Util.formatPrice(p.getValue().priceProperty())));
            catalogueTable.getColumns().add(retailPriceColumn);

            TableColumn<CatalogueItemModel, String> dateStockedColumn = new TableColumn<>("Date First Stocked");
            dateStockedColumn.setCellValueFactory(p -> Bindings.concat(Util.formatHumanDate(p.getValue().getStockedOn())));
            catalogueTable.getColumns().add(dateStockedColumn);

            TableColumn<CatalogueItemModel, String> stockColumn = new TableColumn<>("Total Stock");
            stockColumn.setCellValueFactory(p -> Bindings.concat(p.getValue().stockTotalProperty()));
            catalogueTable.getColumns().add(stockColumn);
        }

        catalogueTable.setRowFactory(param -> {
            TableRow<CatalogueItemModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    CatalogueItemModel item = row.getItem();
                    openEdit(item);
                }
            });
            return row;
        });
    }

    private void activateView(@NotNull ScrollPane view) {
        view.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onEdit() {
        CatalogueItemModel item = catalogueTable.getSelectionModel().getSelectedItem();
        if (item != null) openEdit(item);
    }

    public void onCloseEdit() {
        toolDrawer.close();
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    @Override
    public void onFilter() {
        activateView(filterMenu);
    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }

    public void onCancelSearch() {
        // TODO cancel search action
        toolDrawer.close();
    }

    private void openEdit(@NotNull CatalogueItemModel item) {
        logger.info("Item Clicked: {}", item);
        activateView(editMenu);
    }
}
