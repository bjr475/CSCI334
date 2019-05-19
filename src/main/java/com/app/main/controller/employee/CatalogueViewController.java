package com.app.main.controller.employee;

import com.app.main.Util;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CatalogueViewController extends AChildEmployeeViewController implements IEditorActionItem {
    private static final Logger logger = LogManager.getLogger(CatalogueViewController.class.getName());

    /* Tool Drawer */
    public JFXDrawer toolDrawer;

    /* Edit Control */
    public ScrollPane editMenu;
    public TextField editItemName;
    public TextField editItemID;
    public ChoiceBox<String> editModelType;
    public ChoiceBox<String> editSubject;
    public TextField editPrice;
    public TextArea editDescription;
    public TableView<CatalogueItemLocationModel> editStores;
    public TableView<CatalogueItemSupplierModel> editSuppliers;

    /* Add Control */
    public ScrollPane addMenu;
    public TextField addItemName;
    public TextField addItemID;
    public ChoiceBox<String> addType;
    public ChoiceBox<String> addSubject;
    public TextField addPrice;
    public TextArea addDescription;
    public TableView<CatalogueItemLocationModel> addStoresView;
    public TableView<CatalogueItemSupplierModel> addSuppliersView;

    /* Filter Control */
    public ScrollPane filterMenu;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

    /* Catalogue Table */
    public TableView<CatalogueItemModel> catalogueTable;

    /* Add and Edit Values */
    private ObjectProperty<CatalogueItemModel> currentAddItem;
    private ObjectProperty<CatalogueItemModel> currentEditableItem;

    public CatalogueViewController(ApplicationModel model) {
        super(model);
        currentAddItem = new SimpleObjectProperty<>(null);
        currentEditableItem = new SimpleObjectProperty<>(null);

        currentAddItem.addListener(this::onUpdateAddItem);
        currentEditableItem.addListener(this::onUpdateEditItem);
    }

    private void unbindItemModel(@Nullable CatalogueItemModel item, @NotNull TextField name, @NotNull TextField id,
                                 ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                                 @NotNull TextArea description,
                                 @NotNull TableView<CatalogueItemLocationModel> storesView,
                                 @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            name.textProperty().unbindBidirectional(item.nameProperty());
            id.setText("-- Item ID --");
            type.valueProperty().unbindBidirectional(item.typeProperty());
            subject.valueProperty().unbindBidirectional(item.subjectProperty());
            price.setText("-- Price --");
            description.textProperty().unbindBidirectional(item.descriptionProperty());
            storesView.itemsProperty().unbindBidirectional(item.storesProperty());
            suppliersView.itemsProperty().unbindBidirectional(item.suppliersProperty());
        }
    }

    private void bindItemModel(@Nullable CatalogueItemModel item, @NotNull TextField name, @NotNull TextField id,
                               ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                               @NotNull TextArea description,
                               @NotNull TableView<CatalogueItemLocationModel> storesView,
                               @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            name.textProperty().bindBidirectional(item.nameProperty());
            id.setText(item.getItemId());
            type.valueProperty().bindBidirectional(item.typeProperty());
            subject.valueProperty().bindBidirectional(item.subjectProperty());
            price.setText(Util.formatPrice(item.priceProperty()));
            description.textProperty().bindBidirectional(item.descriptionProperty());
            storesView.itemsProperty().bindBidirectional(item.storesProperty());
            suppliersView.itemsProperty().bindBidirectional(item.suppliersProperty());
        }
    }

    private void onUpdateAddItem(@SuppressWarnings("unused") ObservableValue<? extends CatalogueItemModel> observable,
                                 CatalogueItemModel oldValue, CatalogueItemModel newValue) {
        unbindItemModel(
                oldValue,
                addItemName, addItemID, addType, addSubject,
                addPrice, addDescription, addStoresView, addSuppliersView
        );
        bindItemModel(
                newValue,
                addItemName, addItemID, addType, addSubject,
                addPrice, addDescription, addStoresView, addSuppliersView
        );
        addStoresView.refresh();
        addSuppliersView.refresh();
    }

    private void onUpdateEditItem(@SuppressWarnings("unused") ObservableValue<? extends CatalogueItemModel> observable,
                                  CatalogueItemModel oldValue, CatalogueItemModel newValue) {
        unbindItemModel(
                oldValue,
                editItemName, editItemID, editModelType, editSubject,
                editPrice, editDescription, editStores, editSuppliers
        );
        bindItemModel(
                newValue,
                editItemName, editItemID, editModelType, editSubject,
                editPrice, editDescription, editStores, editSuppliers
        );
        editStores.refresh();
        editSuppliers.refresh();
    }

    private void buildCatalogueTable() {
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
            dateStockedColumn.setCellValueFactory(p -> Bindings.concat(Util.formatPrintDate(p.getValue().getStockedOn())));
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

    private void buildEditTables() {
        {
            TableColumn<CatalogueItemLocationModel, String> itemIdColumn = new TableColumn<>("Store Name");
            itemIdColumn.setCellValueFactory(p -> p.getValue().storeProperty());
            editStores.getColumns().add(itemIdColumn);

            TableColumn<CatalogueItemLocationModel, String> nameColumn = new TableColumn<>("Quantity");
            nameColumn.setCellValueFactory(p -> Bindings.concat(String.format("%d", p.getValue().getCount())));
            editStores.getColumns().add(nameColumn);
        }

        {
            TableColumn<CatalogueItemSupplierModel, String> itemIdColumn = new TableColumn<>("Supplier Name");
            itemIdColumn.setCellValueFactory(p -> p.getValue().nameProperty());
            editSuppliers.getColumns().add(itemIdColumn);

            TableColumn<CatalogueItemSupplierModel, String> nameColumn = new TableColumn<>("Price");
            nameColumn.setCellValueFactory(p -> Bindings.concat(Util.formatPrice(p.getValue().getPrice())));
            editSuppliers.getColumns().add(nameColumn);
        }
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
        buildCatalogueTable();
        buildEditTables();

        ArrayList<CatalogueItemModel> models = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            CatalogueItemModel catalogueItem = new CatalogueItemModel(String.format("%d", i));
            catalogueItem.setName(String.format("Name %d", i));
            for (int j = 0; j < 5; j++) {
                CatalogueItemLocationModel location = new CatalogueItemLocationModel();
                location.setStore(String.format("Store %d", j));
                location.setCount(j);
                catalogueItem.getStores().add(location);

                CatalogueItemSupplierModel supplier = new CatalogueItemSupplierModel();
                supplier.setName(String.format("Supplier %d", i));
                catalogueItem.getSuppliers().add(supplier);
            }
            models.add(catalogueItem);
        }
        catalogueTable.setItems(FXCollections.observableArrayList(models));
        catalogueTable.refresh();

        currentAddItem.set(new CatalogueItemModel(String.format("NewItem-%d", catalogueTable.getItems().size())));
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

    private void openEdit(@NotNull CatalogueItemModel item) {
        logger.info("Item Clicked: {}", item);
        currentEditableItem.set(new CatalogueItemModel(item));
        activateView(editMenu);
    }

    public void onConfirmEdit() {
        CatalogueItemModel model = currentEditableItem.get();
        if (model != null) catalogueTable.getSelectionModel().getSelectedItem().set(model);
        catalogueTable.refresh();
        toolDrawer.close();
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
        currentAddItem.set(new CatalogueItemModel(String.format("NewItem-%d", catalogueTable.getItems().size())));
    }

    public void onConfirmAdd() {
        catalogueTable.itemsProperty().get().add(currentAddItem.get());
        catalogueTable.refresh();
        currentAddItem.set(new CatalogueItemModel(String.format("NewItem-%d", catalogueTable.getItems().size())));
    }

    @Override
    public void onFilter() {
        activateView(filterMenu);
    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }

    public void resetSearch() {
        searchWords.setText("");
    }

    public void confirmSearch() {
        logger.info(
                "Looking for catalogue items that contain the following words: '{}'",
                searchWords.getText()
        );
    }
}
