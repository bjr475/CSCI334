package com.app.main.controller.employee;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class CatalogueViewController extends AChildEmployeeEditorActionViewController {
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
    public ChoiceBox<String> addType;
    public ChoiceBox<String> addSubject;
    public TextField addPrice;
    public TextArea addDescription;
    public TableView<CatalogueItemLocationModel> addStoresView;
    public TableView<CatalogueItemSupplierModel> addSuppliersView;

    /*Store Dialog*/
    public JFXDialog addStoreDialog;
    public ListView storeList;
    public TableView storeAvailabilityView;
    public TextField selectedStore;
    public TextField itemQuantity;

    /*Supplier Dialog*/
    public JFXDialog addSupplierDialog;
    public ListView supplierList;
    public TableView itemSupplierView;
    public TextField selectedSupplier;
    public TextField itemPrice;

    /* Filter Control */
    public ScrollPane filterMenu;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

    /* Catalogue Table */
    public TableView<CatalogueItemModel> catalogueTable;
    public StackPane parentCataloguePane;

    /* Add and Edit Values */
    private ObjectProperty<CatalogueItemModel> currentAddItem;
    private ObjectProperty<CatalogueItemModel> currentEditableItem;

    public CatalogueViewController(ApplicationModel model) {
        super(model);
        currentAddItem = new SimpleObjectProperty<>(null);
        currentEditableItem = new SimpleObjectProperty<>(null);

        currentAddItem.addListener(this::onUpdateAddItem);
        currentEditableItem.addListener(this::onUpdateEditItem);
        getModel().currentUserProperty().addListener(this::userChanged);
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {
        boolean state = permissions.isCreateItem() || permissions.isModifyItem();
        editProperty().set(permissions.isModifyItem());
        addProperty().set(permissions.isCreateItem());

        setEditable(
                state,
                editItemName, editPrice, editDescription, addItemName, addPrice, addDescription
        );
        setEditable(
                state,
                editModelType, editSubject, addType, addSubject
        );
        setEditable(
                state, editStores, editSuppliers, addStoresView, addSuppliersView
        );
    }

    @Override
    protected void setAdminEditable() {
        setEditable(
                true,
                editItemName, editPrice, editDescription, addItemName, addPrice, addDescription
        );
        setEditable(
                true,
                editModelType, editSubject, addType, addSubject
        );
        setEditable(
                true, editStores, editSuppliers, addStoresView, addSuppliersView
        );
    }

    private void unbindItemModelAdd(@Nullable CatalogueItemModel item, @NotNull TextField name,
                                    ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                                    @NotNull TextArea description,
                                    @NotNull TableView<CatalogueItemLocationModel> storesView,
                                    @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            name.textProperty().unbindBidirectional(item.nameProperty());
            type.valueProperty().unbindBidirectional(item.typeProperty());
            subject.valueProperty().unbindBidirectional(item.subjectProperty());
            price.setText("-- Price --");
            description.textProperty().unbindBidirectional(item.descriptionProperty());
            storesView.itemsProperty().unbindBidirectional(item.storesProperty());
            suppliersView.itemsProperty().unbindBidirectional(item.suppliersProperty());
        }
    }

    private void bindItemModelAdd(@Nullable CatalogueItemModel item, @NotNull TextField name,
                                  ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                                  @NotNull TextArea description,
                                  @NotNull TableView<CatalogueItemLocationModel> storesView,
                                  @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            name.textProperty().bindBidirectional(item.nameProperty());
            type.valueProperty().bindBidirectional(item.typeProperty());
            subject.valueProperty().bindBidirectional(item.subjectProperty());
            price.setText(Util.formatPrice(item.priceProperty()));
            description.textProperty().bindBidirectional(item.descriptionProperty());
            storesView.itemsProperty().bindBidirectional(item.storesProperty());
            suppliersView.itemsProperty().bindBidirectional(item.suppliersProperty());
        }
    }

    private void unbindItemModel(@Nullable CatalogueItemModel item, @NotNull TextField name, @NotNull TextField id,
                                 ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                                 @NotNull TextArea description,
                                 @NotNull TableView<CatalogueItemLocationModel> storesView,
                                 @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            id.setText("-- Item ID --");
            unbindItemModelAdd(item, name, type, subject, price, description, storesView, suppliersView);
        }
    }

    private void bindItemModel(@Nullable CatalogueItemModel item, @NotNull TextField name, @NotNull TextField id,
                               ChoiceBox<String> type, ChoiceBox<String> subject, @NotNull TextField price,
                               @NotNull TextArea description,
                               @NotNull TableView<CatalogueItemLocationModel> storesView,
                               @NotNull TableView<CatalogueItemSupplierModel> suppliersView) {
        if (item != null) {
            id.setText(Util.formatModelId(item));
            bindItemModelAdd(item, name, type, subject, price, description, storesView, suppliersView);
        }
    }

    private void onUpdateAddItem(@SuppressWarnings("unused") ObservableValue<? extends CatalogueItemModel> observable,
                                 CatalogueItemModel oldValue, CatalogueItemModel newValue) {
        unbindItemModelAdd(
                oldValue,
                addItemName, addType, addSubject,
                addPrice, addDescription, addStoresView, addSuppliersView
        );
        bindItemModelAdd(
                newValue,
                addItemName, addType, addSubject,
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

    private void userChanged(ObservableValue<? extends AUserModel> observable, AUserModel oldValue, AUserModel newValue) {
        catalogueTable.setItems(FXCollections.observableArrayList());
        if (newValue != null) updateCatalogueTable();
        if (oldValue != null) toolDrawer.close();
    }

    private void updateCatalogueTable() {
        currentAddItem.set(new CatalogueItemModel(catalogueTable.getItems().size()));
        currentEditableItem.set(null);
        catalogueTable.setItems(FXCollections.observableArrayList(Database.INSTANCE.getModel().getModels()));
        catalogueTable.refresh();
    }

    private void buildCatalogueTable() {
        TableColumn<CatalogueItemModel, Number> itemIdColumn = new TableColumn<>("Item No");
        itemIdColumn.setMinWidth(150);
        itemIdColumn.setCellValueFactory(p -> p.getValue().itemIdProperty());
        itemIdColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CatalogueItemModel, Number> call(TableColumn<CatalogueItemModel, Number> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        if (!empty && item != null) {
                            setGraphic(new Label(Util.formatModelId(item)));
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                };
            }
        });
        catalogueTable.getColumns().add(itemIdColumn);

        TableColumn<CatalogueItemModel, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(240);
        nameColumn.setCellValueFactory(p -> p.getValue().nameProperty());
        catalogueTable.getColumns().add(nameColumn);

        TableColumn<CatalogueItemModel, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(120);
        typeColumn.setCellValueFactory(p -> p.getValue().typeProperty());
        catalogueTable.getColumns().add(typeColumn);

        TableColumn<CatalogueItemModel, String> subjectColumn = new TableColumn<>("Subject Area");
        subjectColumn.setMinWidth(120);
        subjectColumn.setCellValueFactory(p -> p.getValue().subjectProperty());
        catalogueTable.getColumns().add(subjectColumn);

        TableColumn<CatalogueItemModel, String> retailPriceColumn = new TableColumn<>("Retail Price");
        retailPriceColumn.setMinWidth(120);
        retailPriceColumn.setCellValueFactory(p -> Bindings.concat(Util.formatPrice(p.getValue().priceProperty())));
        catalogueTable.getColumns().add(retailPriceColumn);

        TableColumn<CatalogueItemModel, String> dateStockedColumn = new TableColumn<>("Date First Stocked");
        dateStockedColumn.setMinWidth(120);
        dateStockedColumn.setCellValueFactory(p -> Bindings.concat(Util.formatPrintDate(p.getValue().getStockedOn())));
        catalogueTable.getColumns().add(dateStockedColumn);

        TableColumn<CatalogueItemModel, String> stockColumn = new TableColumn<>("Total Stock");
        stockColumn.setMinWidth(120);
        stockColumn.setCellValueFactory(p -> Bindings.concat(p.getValue().stockTotalProperty()));
        catalogueTable.getColumns().add(stockColumn);

        TableColumn<CatalogueItemModel, ObservableList<CatalogueItemSupplierModel>> suppliersColumn = new TableColumn<>("Suppliers");
        suppliersColumn.setMinWidth(120);
        suppliersColumn.setCellValueFactory(p -> p.getValue().suppliersProperty());
        suppliersColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CatalogueItemModel, ObservableList<CatalogueItemSupplierModel>> call(TableColumn<CatalogueItemModel, ObservableList<CatalogueItemSupplierModel>> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(ObservableList<CatalogueItemSupplierModel> item, boolean empty) {
                        if (!empty && item != null) {
                            setGraphic(
                                    new Label(
                                            item.stream().map(CatalogueItemSupplierModel::getName).collect(
                                                    Collectors.joining(", ")
                                            )
                                    )
                            );
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                };
            }
        });
        catalogueTable.getColumns().add(suppliersColumn);

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

        currentAddItem.set(new CatalogueItemModel(catalogueTable.getItems().size()));
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
        AUserModel user = getModel().getCurrentUser();
        boolean canEdit = (user.getUserType() != AUserModel.UserType.EMPLOYEE) || ((EmployeeModel) user).getPermissions().isModifyItem();
        if (canEdit) {
            logger.info("Item Clicked: {}", item);
            currentEditableItem.set(new CatalogueItemModel(item));
            activateView(editMenu);
        }
    }

    public void onConfirmEdit() {
        CatalogueItemModel model = currentEditableItem.get();
        if (model != null) {
            Database.INSTANCE.getModel().updateModel(model);
            updateCatalogueTable();
            toolDrawer.close();
        }
    }

    @Override
    public void onAdd() {
        AUserModel user = getModel().getCurrentUser();
        boolean canEdit = (user.getUserType() != AUserModel.UserType.EMPLOYEE) || ((EmployeeModel) user).getPermissions().isCreateItem();
        if (canEdit) activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
        currentAddItem.set(new CatalogueItemModel(catalogueTable.getItems().size()));
    }

    public void onConfirmAdd() {
        if (Database.INSTANCE.getModel().saveModel(currentAddItem.get())) updateCatalogueTable();
    }

    public void cancelStoreDialog() {
        addStoreDialog.close();
    }

    public void saveStoreDialog() {
        addStoreDialog.close();
    }

    public void cancelSupplierDialog() {
        addSupplierDialog.close();
    }

    public void saveSupplierDialog() {
        addSupplierDialog.close();
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

    public void addSupplier(ActionEvent event) {
        addSupplierDialog.show(parentCataloguePane);
    }

    public void addStore(ActionEvent event) {
        addStoreDialog.show(parentCataloguePane);
    }
}
