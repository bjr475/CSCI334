package com.app.main.controller.employee.supplier;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.controller.AddressViewController;
import com.app.main.controller.employee.AChildEmployeeEditorActionViewController;
import com.app.main.controller.employee.SearchableComboBoxUtil;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import com.app.main.model.supplier.SupplierCatalogueItemModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierItemModel;
import com.app.main.model.supplier.SupplierModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class SuppliersViewController extends AChildEmployeeEditorActionViewController {
    private static final Logger logger = LogManager.getLogger(SuppliersViewController.class.getName());

    public JFXDrawer toolDrawer;
    public TableView<SupplierModel> suppliers;
    public StackPane parentSuppliersPane;
    public ScrollPane searchMenu;

    /*Edit Menu*/
    public ScrollPane editMenu;
    public TextField editSupplierName;
    public AddressViewController editAddressController;
    public TableView<SupplierContactModel> editContactTable;
    public TableView<SupplierCatalogueItemModel> editItemsTable;
    public ComboBox<CatalogueItemIdNameModel> editItemSearch;
    public Spinner<Double> editItemPrice;
    public Spinner<Double> editCredit;

    /*Add Menu*/
    public ScrollPane addMenu;
    public TextField addSupplierName;
    public AddressViewController addAddressController;
    public TableView<SupplierContactModel> addContactTable;
    public TableView<SupplierCatalogueItemModel> addItemsTable;
    public Spinner<Double> addItemPrice;
    public ComboBox<CatalogueItemIdNameModel> addItemSearch;
    public JFXCheckBox addExistingCredit;
    public Spinner<Double> addCredit;

    /*Contact Dialog*/
    public SupplierContactViewController addContactController;

    /*Item Dialog*/
    public SupplierItemViewController addItemController;

    /* Add Values */
    private ObjectProperty<SupplierModel> currentAddSupplier;
    private ObjectProperty<SupplierContactModel> currentAddSupplierContact;
    private ObjectProperty<ObservableList<SupplierCatalogueItemModel>> currentAddSupplierCatalogueItems;

    /* Edit Values */
    private ObjectProperty<SupplierModel> currentEditableSupplier;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) updateSuppliersTable();
        });

        currentAddSupplier = new SimpleObjectProperty<>(null);
        currentAddSupplierContact = new SimpleObjectProperty<>(null);
        currentAddSupplierCatalogueItems = new SimpleObjectProperty<>(FXCollections.observableArrayList());

        currentAddSupplier.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addSupplierName.textProperty().unbindBidirectional(oldValue.nameProperty());
                addAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                addContactTable.itemsProperty().unbindBidirectional(oldValue.contactDetailsProperty());
                oldValue.contactDetailsProperty().get().removeListener(this::addSuppliersContactChanged);
            }
            if (newValue != null) {
                addSupplierName.textProperty().bindBidirectional(newValue.nameProperty());
                addAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                addContactTable.itemsProperty().bindBidirectional(newValue.contactDetailsProperty());
                addCredit.getValueFactory().setValue(newValue.getCreditLine());
                newValue.contactDetailsProperty().get().addListener(this::addSuppliersContactChanged);
                addItemSearch.itemsProperty().get().clear();
                addItemSearch.itemsProperty().get().addAll(Database.INSTANCE.getModel().getIdNameModels());
            }
        });
        currentAddSupplierCatalogueItems.addListener((observable, oldValue, newValue) -> {
            updateAddSupplierItemTable();
            if (oldValue != null) oldValue.removeListener(this::addSupplierItemsChanged);
            if (newValue != null) newValue.addListener(this::addSupplierItemsChanged);
        });

        currentEditableSupplier = new SimpleObjectProperty<>(null);
        currentEditableSupplier.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                editSupplierName.textProperty().unbindBidirectional(oldValue.nameProperty());
                editAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                editContactTable.itemsProperty().unbindBidirectional(oldValue.contactDetailsProperty());
            }
            if (newValue != null) {
                editSupplierName.textProperty().bindBidirectional(newValue.nameProperty());
                editAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                editContactTable.itemsProperty().bindBidirectional(newValue.contactDetailsProperty());
                editCredit.getValueFactory().setValue(newValue.getCreditLine());
                editItemSearch.itemsProperty().get().clear();
                editItemSearch.itemsProperty().get().addAll(Database.INSTANCE.getModel().getIdNameModels());

                editItemPrice.getValueFactory().setValue(0.);

//                editSupplierSto.itemsProperty().get().clear();
                // TODO ids?
//                for (StoreModel store : Database.INSTANCE.getStore().getStores()) {
//                    addSupplierItemStoreName.itemsProperty().get().add(store.getName());
//            }
            }
        });
    }

    private void addSupplierItemsChanged(@SuppressWarnings("unused") ListChangeListener.Change<? extends SupplierCatalogueItemModel> c) {
        updateAddSupplierItemTable();
    }

    private void addSuppliersContactChanged(@SuppressWarnings("unused") ListChangeListener.Change<? extends SupplierContactModel> c) {
        addContactTable.refresh();
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {
        setEditableAdd(permissions.isCreateCustomer());
        setEditableEdit(permissions.isModifyCustomer());
    }

    @Override
    protected void setAdminEditable() {
        setEditableAdd(true);
        setEditableEdit(true);
    }

    private void setEditableAdd(boolean state) {
        setEditable(
                state,
                addSupplierName
        );
        setEditable(
                state,
                addContactTable, addItemsTable
        );
        setEditable(
                state,
                addExistingCredit
        );
        setEditable(
                state,
                addItemPrice, addCredit
        );
        addItemSearch.setDisable(!state);
        addAddressController.setEditable(state);
    }

    private void setEditableEdit(boolean state) {
        setEditable(
                state,
                editSupplierName
        );
        setEditable(
                state,
                editContactTable, editItemsTable
        );
        setEditable(
                state,
                editItemPrice, editCredit
        );
        editAddressController.setEditable(state);
        editItemSearch.setDisable(!state);
    }

    private void updateSuppliersTable() {
        suppliers.setItems(FXCollections.observableArrayList(Database.INSTANCE.getSupplier().getSuppliers()));
        suppliers.refresh();
    }

    private void updateAddSupplierItemTable() {
        addItemsTable.setItems(currentAddSupplierCatalogueItems.get());
        addItemsTable.refresh();
    }

    private void buildSupplierTable() {
        TableColumn<SupplierModel, String> nameColumn = new TableColumn<>("Supplier Name");
        nameColumn.setCellValueFactory(p -> p.getValue().nameProperty());
        suppliers.getColumns().add(nameColumn);

        TableColumn<SupplierModel, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(p -> Bindings.concat(p.getValue().getAddress().format()));
        suppliers.getColumns().add(address);

        TableColumn<SupplierModel, SupplierContactModel> contactName = new TableColumn<>("Contact Name");
        contactName.setCellValueFactory(p -> p.getValue().primaryContactProperty());
        contactName.setCellFactory((p) -> new TableCell<>() {
                    @Override
                    protected void updateItem(SupplierContactModel item, boolean empty) {
                        if (item != null && !empty) {
                            Label label = new Label(String.format("%s", item.getName()));
                            setGraphic(label);
                        }
                    }
                }
        );
        suppliers.getColumns().add(contactName);

        TableColumn<SupplierModel, SupplierContactModel> contactEmail = new TableColumn<>("Contact Email");
        contactEmail.setCellValueFactory(p -> p.getValue().primaryContactProperty());
        contactEmail.setCellFactory((p) -> new TableCell<>() {
                    @Override
                    protected void updateItem(SupplierContactModel item, boolean empty) {
                        if (item != null && !empty) {
                            String email = item.getEmail();
                            Label label = new Label(String.format("%s", email == null ? "N/A" : email));
                            setGraphic(label);
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                }
        );
        suppliers.getColumns().add(contactEmail);

        TableColumn<SupplierModel, SupplierContactModel> contactPhone = new TableColumn<>("Contact Phone");
        contactPhone.setCellValueFactory(p -> p.getValue().primaryContactProperty());
        contactPhone.setCellFactory((p) -> new TableCell<>() {
                    @Override
                    protected void updateItem(SupplierContactModel item, boolean empty) {
                        if (item != null && !empty) {
                            String phone = item.getPhone();
                            setGraphic(new Label(String.format("%s", phone == null ? "N/A" : phone)));
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                }
        );
        suppliers.getColumns().add(contactPhone);

        suppliers.setRowFactory(param -> {
            TableRow<SupplierModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) openEdit(row.getItem());
            });
            return row;
        });
    }

    private void buildSupplierContact(@NotNull TableView<SupplierContactModel> contactView) {
        TableColumn<SupplierContactModel, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        contactView.getColumns().add(nameColumn);
        TableColumn<SupplierContactModel, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(param -> param.getValue().phoneProperty());
        contactView.getColumns().add(phoneColumn);
        TableColumn<SupplierContactModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> param.getValue().emailProperty());
        contactView.getColumns().add(emailColumn);
        TableColumn<SupplierContactModel, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty && item != null) {
                    JFXButton button = new JFXButton("Remove");
                    button.setOnAction(event -> logger.info("Contact Remove Pressed: {}", event));
                    setGraphic(button);
                } else {
                    super.updateItem(item, empty);
                }
            }
        });
        contactView.getColumns().add(actionColumn);
    }

    private void buildSupplierItems(@NotNull TableView<SupplierCatalogueItemModel> catalogueView) {
        TableColumn<SupplierCatalogueItemModel, Number> itemNumber = new TableColumn<>("Item No.");
        itemNumber.setCellValueFactory(param -> param.getValue().getCatalogueItem().itemIdProperty());
        itemNumber.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                if (!empty && item != null) setGraphic(new Label(Util.formatId(item)));
                else super.updateItem(item, empty);
            }
        });
        catalogueView.getColumns().add(itemNumber);
        TableColumn<SupplierCatalogueItemModel, String> itemName = new TableColumn<>("Name");
        itemName.setCellValueFactory(param -> param.getValue().getCatalogueItem().nameProperty());
        catalogueView.getColumns().add(itemName);
        TableColumn<SupplierCatalogueItemModel, Number> itemPrice = new TableColumn<>("Price");
        itemPrice.setCellValueFactory(param -> param.getValue().getCatalogueItem().priceProperty());
        itemPrice.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                if (!empty && item != null) setGraphic(new Label(Util.formatPrice(item.doubleValue())));
                else super.updateItem(item, empty);
            }
        });
        catalogueView.getColumns().add(itemPrice);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        /* Add Supplier */
        {
            buildSupplierTable();
            buildSupplierItems(addItemsTable);
            buildSupplierContact(addContactTable);

            SearchableComboBoxUtil.setCatalogueIdModelConverter(addItemSearch);
            SearchableComboBoxUtil.createSearchableComboBox(addItemSearch, SearchableComboBoxUtil.CATALOGUE_COMPARATOR);

            currentAddSupplier.set(new SupplierModel(0));
            currentAddSupplierContact.set(new SupplierContactModel());
        }

        /* Edit Supplier */
        {
            buildSupplierItems(editItemsTable);
            buildSupplierContact(editContactTable);

            SearchableComboBoxUtil.setCatalogueIdModelConverter(editItemSearch);
            SearchableComboBoxUtil.createSearchableComboBox(editItemSearch, SearchableComboBoxUtil.CATALOGUE_COMPARATOR);
//            SearchableComboBoxUtil.createSearchableComboBox(editSupplierItemStoreName, (input, object) -> object.contains(input));
        }
    }

    private void activateView(@NotNull Control pane) {
        pane.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
        currentAddSupplier.set(new SupplierModel(0));
        currentAddSupplierContact.set(new SupplierContactModel());
    }

    public void onConfirmAdd() {
        Database.INSTANCE.getSupplier().saveSupplier(currentAddSupplier.get(), currentAddSupplierCatalogueItems.get());
        getModel().currentUserProperty().set(getModel().getCurrentUser());
//        updateSuppliersTable();
        onCancelAdd();
    }

    public void openAddContact() {
        addContactController.open(parentSuppliersPane, contact -> currentAddSupplier.get().getContactDetails().add(contact));
    }

    public void openAddItem() {
        addItemController.open(parentSuppliersPane, item -> {
            currentAddSupplierCatalogueItems.get().add(item);
            logger.info("A new Item has been added: {}", item);
            addItemsTable.setItems(currentAddSupplierCatalogueItems.get());
            addItemsTable.refresh();
        });
    }

    public void confirmSearchAddItemSupplier() {
        CatalogueItemIdNameModel item = SearchableComboBoxUtil.getComboBoxValue(addItemSearch);
        if (item != null) {
            currentAddSupplierCatalogueItems.get().add(new SupplierCatalogueItemModel(
                    Database.INSTANCE.getModel().getModel(item.getId()),
                    new SupplierItemModel()
            ));
            SearchableComboBoxUtil.clearComboBoxValue(addItemSearch);
        }
    }

    private void openEdit(SupplierModel model) {
        if (model != null) {
            currentEditableSupplier.set(model);
            activateView(editMenu);
        }
    }

    @Override
    public void onEdit() {
        openEdit(suppliers.getSelectionModel().getSelectedItem());
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }

    public void openEditAddContact(ActionEvent event) {

    }

    public void confirmSearchEditItemSupplier(ActionEvent event) {

    }

    public void onCancelEdit(ActionEvent event) {

    }

    public void onConfirmEdit(ActionEvent event) {

    }

    public void openEditItem(ActionEvent event) {

    }
}
