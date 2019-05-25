package com.app.main.controller.employee;

import com.app.database.Database;
import com.app.main.model.ApplicationModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

public class SuppliersViewController extends AChildEmployeeEditorActionViewController {
    public JFXDrawer toolDrawer;
    public TableView<SupplierModel> suppliers;
    public ScrollPane searchMenu;

    /*Edit Menu*/
    public ScrollPane editMenu;

    /*Add Menu*/
    public ScrollPane addMenu;

    /*Contact Dialog*/
    public JFXDialog addContactDialog;

    /*Item Dialog*/
    public JFXDialog addItemDialog;

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
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        buildSupplierTable();
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
