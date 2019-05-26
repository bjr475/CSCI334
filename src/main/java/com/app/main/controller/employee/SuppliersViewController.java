package com.app.main.controller.employee;

import com.app.database.Database;
import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierItemModel;
import com.app.main.model.supplier.SupplierModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
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
import javafx.util.Callback;
import javafx.util.StringConverter;
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

    /*Add Menu*/
    public ScrollPane addMenu;
    public TextField addSupplierName;
    public AddressViewController addAddressController;
    public TableView<SupplierContactModel> addContactTable;
    public TableView<SupplierItemModel> addItemsTable;
    public ComboBox<CatalogueItemIdNameModel> addItemSearch;
    public JFXCheckBox addExistingCredit;
    public Spinner<Double> addCredit;

    /*Contact Dialog*/
    public JFXDialog addContactDialog;
    public TextField addContactName;
    public TextField addContactPhone;
    public TextField addContactEmail;

    /*Item Dialog*/
    public JFXDialog addSupplierItemDialog;
    public TextField addSupplierItemName;
    public ChoiceBox<String> addSupplierItemType;
    public ChoiceBox<String> addSupplierItemSubject;
    public Spinner<Double> addSupplierItemPrice;
    public TextArea addSupplierItemDescription;
    public TableView<CatalogueItemLocationModel> addSupplierItemStores;
    public ComboBox<String> addSupplierItemStore;

    /* Add Values */
    private ObjectProperty<SupplierModel> currentAddSupplier;
    private ObjectProperty<SupplierContactModel> currentAddSupplierContact;
    private ObjectProperty<CatalogueItemModel> currentAddSupplierCatalogueItem;
    private ObjectProperty<ObservableList<CatalogueItemModel>> currentAddSupplierCatalogueItems;

    /* Edit Values */
    private ObjectProperty<SupplierModel> currentEditableSupplier;

    public SuppliersViewController(ApplicationModel model) {
        super(model);

        currentEditableSupplier = new SimpleObjectProperty<>(null);
        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) updateSuppliersTable();
        });

        currentAddSupplier = new SimpleObjectProperty<>(null);
        currentAddSupplierContact = new SimpleObjectProperty<>(null);
        currentAddSupplierCatalogueItem = new SimpleObjectProperty<>(null);
        currentAddSupplierCatalogueItems = new SimpleObjectProperty<>(FXCollections.observableArrayList());

        currentAddSupplier.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addSupplierName.textProperty().unbindBidirectional(oldValue.nameProperty());
                addAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                addContactTable.itemsProperty().unbindBidirectional(oldValue.contactDetailsProperty());
                addItemsTable.itemsProperty().unbindBidirectional(oldValue.modelsProperty());
                oldValue.contactDetailsProperty().get().removeListener(this::addSuppliersContactChanged);
            }
            if (newValue != null) {
                addSupplierName.textProperty().bindBidirectional(newValue.nameProperty());
                addAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                addContactTable.itemsProperty().bindBidirectional(newValue.contactDetailsProperty());
                addItemsTable.itemsProperty().bindBidirectional(newValue.modelsProperty());
                addCredit.getValueFactory().setValue(newValue.getCreditLine());
                newValue.contactDetailsProperty().get().addListener(this::addSuppliersContactChanged);

                // TODO ask database for these values
                addItemSearch.itemsProperty().get().clear();
                addItemSearch.itemsProperty().get().addAll(
                        new CatalogueItemIdNameModel(0, "Model 0"),
                        new CatalogueItemIdNameModel(1, "Model 1"),
                        new CatalogueItemIdNameModel(2, "Model 2"),
                        new CatalogueItemIdNameModel(3, "Model 3")
                );

                addSupplierItemStore.itemsProperty().get().clear();
                addSupplierItemStore.itemsProperty().get().addAll(
                        "Store A",
                        "Store B",
                        "Store C",
                        "Store D"
                );
            }
        });
        currentAddSupplierContact.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addContactName.textProperty().unbindBidirectional(oldValue.nameProperty());
                addContactPhone.textProperty().unbindBidirectional(oldValue.phoneProperty());
                addContactEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
            }
            if (newValue != null) {
                addContactName.textProperty().bindBidirectional(newValue.nameProperty());
                addContactPhone.textProperty().bindBidirectional(newValue.phoneProperty());
                addContactEmail.textProperty().bindBidirectional(newValue.emailProperty());
            }
        });
        currentAddSupplierCatalogueItem.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addSupplierItemName.textProperty().unbindBidirectional(oldValue.nameProperty());
                addSupplierItemType.valueProperty().unbindBidirectional(oldValue.typeProperty());
                addSupplierItemSubject.valueProperty().unbindBidirectional(oldValue.subjectProperty());
                addSupplierItemDescription.textProperty().unbindBidirectional(oldValue.descriptionProperty());
                addSupplierItemStores.itemsProperty().unbindBidirectional(oldValue.storesProperty());
            }
            if (newValue != null) {
                addSupplierItemName.textProperty().bindBidirectional(newValue.nameProperty());
                addSupplierItemType.valueProperty().bindBidirectional(newValue.typeProperty());
                addSupplierItemSubject.valueProperty().bindBidirectional(newValue.subjectProperty());
                addSupplierItemDescription.textProperty().bindBidirectional(newValue.descriptionProperty());
                addSupplierItemStores.itemsProperty().bindBidirectional(newValue.storesProperty());
                addSupplierItemPrice.getValueFactory().setValue(newValue.getPrice());

            }
        });
    }

    private void addSuppliersContactChanged(ListChangeListener.Change<? extends SupplierContactModel> c) {
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
    }

    private void setEditableEdit(boolean state) {
    }

    private void updateSuppliersTable() {
        suppliers.setItems(FXCollections.observableArrayList(Database.INSTANCE.getSupplier().getSuppliers()));
        suppliers.refresh();
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
                            Label label = new Label(String.format("%s", phone == null ? "N/A" : phone));
                            setGraphic(label);
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

    private void buildAddSuppliersContact() {
        TableColumn<SupplierContactModel, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        addContactTable.getColumns().add(nameColumn);
        TableColumn<SupplierContactModel, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(param -> param.getValue().phoneProperty());
        addContactTable.getColumns().add(phoneColumn);
        TableColumn<SupplierContactModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> param.getValue().emailProperty());
        addContactTable.getColumns().add(emailColumn);
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
        addContactTable.getColumns().add(actionColumn);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        buildSupplierTable();
        buildAddSuppliersContact();

        SearchableComboBoxUtil.setCatalogueIdModelConverter(addItemSearch);
        SearchableComboBoxUtil.createSearchableComboBox(addItemSearch, SearchableComboBoxUtil.CATALOGUE_COMPARATOR);
        SearchableComboBoxUtil.createSearchableComboBox(addSupplierItemStore, (input, object) -> object.contains(input));

        currentAddSupplier.set(new SupplierModel(0));
        currentAddSupplierContact.set(new SupplierContactModel());
    }

    private void activateView(@NotNull Control pane) {
        pane.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    private void openEdit(SupplierModel model) {
        if (model != null) {
            activateView(editMenu);
        }
    }

    @Override
    public void onEdit() {
        openEdit(suppliers.getSelectionModel().getSelectedItem());
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
        onCancelAdd();
    }

    public void cancelAddSupplierContact() {
        addContactDialog.close();
        currentAddSupplierContact.set(new SupplierContactModel());
    }

    public void confirmAddSupplierContact() {
        currentAddSupplier.get().getContactDetails().add(currentAddSupplierContact.get());
        cancelAddSupplierContact();
    }

    public void cancelAddSupplierItem() {
        addSupplierItemDialog.close();
    }

    public void confirmAddSupplierItem() {
        addSupplierItemDialog.close();
    }

    public void addItemConfirm(ActionEvent event) {

    }

    public void openAddContact(ActionEvent event) {
        addContactDialog.show(parentSuppliersPane);
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}
