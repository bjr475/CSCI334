package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.AddressModel;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SuppliersViewController extends AChildEmployeeEditorActionViewController {
    public JFXDrawer toolDrawer;
    public TableView<SupplierModel> suppliers;
    public ScrollPane searchMenu;

    /*Edit Menu*/
    public ScrollPane editMenu;
    public TextField editSupplierName;
    public TextField editSupplierID;
    public TableView editContactTable;
    public TableView editSupplierItemsTable;
    public TextField editItemSearch;
    public JFXButton editSupplierItemButton;
    public TextField editCredit;

    public AddressViewController editAddressViewController;

    /*Add Menu*/
    public ScrollPane addMenu;
    public TextField addSupplierName;
    public TextField addSupplierID;
    public TableView contactTable;
    public TableView supplierItemsTable;
    public TextField itemSearch;
    public JFXButton addSupplierItemButton;
    public CheckBox existingCreditLine;
    public TextField addCredit;

    /*Contact Dialog*/
    public JFXDialog addContactDialog;
    public TextField addContactName;
    public TextField addContactPhone;
    public TextField addContactEmail;

    /*Item Dialog*/
    public JFXDialog addItemDialog;
    public TextField itemNo;
    public TextField itemName;
    public ChoiceBox<String> modelType;
    public ChoiceBox<String> subjectArea;
    public TextField retailPrice;
    public DatePicker dateFirstStocked;
    public TableView<CatalogueItemLocationModel> storeStockTable;
    public TextField itemIDView;
    public TextField quantityView;
    public JFXButton addItemButton;
    public TextArea description;
    public TableView<CatalogueItemSupplierModel> suppliersTable;
    public TextField supplierSearch;
    public TextField price;
    public JFXButton addSupplierButton;

    /* Add and Edit Values */
    private ObjectProperty<SupplierModel> currentAddSupplier;
    private ObjectProperty<SupplierModel> currentEditableSupplier;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
        currentAddSupplier = new SimpleObjectProperty<>(null);
        currentEditableSupplier = new SimpleObjectProperty<>(null);
        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) updateSuppliersTable();
        });
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
                addSupplierName, itemSearch, addCredit
        );
        setEditable(
                state,
                contactTable, supplierItemsTable
        );
        setEditable(
                state,
                addSupplierItemButton, existingCreditLine
        );
    }

    private void setEditableEdit(boolean state) {
        setEditable(
                state,
                editSupplierName, editItemSearch, editCredit
        );
        setEditable(
                state,
                editContactTable, editSupplierItemsTable
        );
        setEditable(
                state,
                editSupplierItemButton
        );
    }

    private void updateSuppliersTable() {
//        suppliers.itemsProperty().set();
        suppliers.refresh();
    }

    private void buildSupplierTable() {
        TableColumn<SupplierModel, String> nameColumn = new TableColumn<>("Supplier Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(p -> p.getValue().nameProperty());
        suppliers.getColumns().add(nameColumn);

        TableColumn<SupplierModel, String> address = new TableColumn<>("Address");
        address.setMinWidth(500);
        address.setCellValueFactory(p -> Bindings.concat(p.getValue().getAddress().format()));
        suppliers.getColumns().add(address);

        TableColumn<SupplierModel, SupplierContactModel> contactName = new TableColumn<>("Contact Name");
        contactName.setMinWidth(200);
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
        contactEmail.setMinWidth(200);
        contactEmail.setCellValueFactory(p -> p.getValue().primaryContactProperty());
        contactEmail.setCellFactory((p) -> new TableCell<>() {
                    @Override
                    protected void updateItem(SupplierContactModel item, boolean empty) {
                        if (item != null && !empty) {
                            Label label = new Label(String.format("%s", item.getEmail()));
                            setGraphic(label);
                        }
                    }
                }
        );
        suppliers.getColumns().add(contactEmail);

        TableColumn<SupplierModel, SupplierContactModel> contactPhone = new TableColumn<>("Contact Phone");
        contactPhone.setMinWidth(200);
        contactPhone.setCellValueFactory(p -> p.getValue().primaryContactProperty());
        contactPhone.setCellFactory((p) -> new TableCell<>() {
                    @Override
                    protected void updateItem(SupplierContactModel item, boolean empty) {
                        if (item != null && !empty) {
                            Label label = new Label(String.format("%s", item.getPhone()));
                            setGraphic(label);
                        }
                    }
                }
        );
        suppliers.getColumns().add(contactPhone);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        buildSupplierTable();

        ArrayList<SupplierModel> supplierModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SupplierModel supplier = new SupplierModel();
            supplier.setName(String.format("Supplier - %d", i));
            supplier.setAddress(new AddressModel("123 Supplier St", "Supplier Area", "Supplier", "2901"));
            SupplierContactModel contact = new SupplierContactModel();
            contact.setPrimary(true);
            contact.setName("Supplier Contact");
            contact.setEmail("contact@supplier.co");
            contact.setPhone("329 874 0711");
            supplier.getContactDetails().add(contact);
            supplierModels.add(supplier);
        }
        suppliers.setItems(FXCollections.observableArrayList(supplierModels));
        suppliers.refresh();


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
    public void onEdit() {
        activateView(editMenu);
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {

    }

    public void onConfirmAdd() {

    }

    public void cancelContactDialog() {
        addContactDialog.close();
    }

    public void saveContactDialog() {
        addContactDialog.close();
    }

    public void cancelItemDialog() {
        addItemDialog.close();
    }

    public void saveItemDialog() {
        addItemDialog.close();
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}
