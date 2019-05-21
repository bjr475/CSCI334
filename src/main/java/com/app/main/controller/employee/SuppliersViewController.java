package com.app.main.controller.employee;

import com.app.main.model.AddressModel;
import com.app.main.model.ApplicationModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SuppliersViewController extends AChildEmployeeViewController implements IEditorActionItem {
    public JFXDrawer toolDrawer;
    public TableView<SupplierModel> suppliers;
    public ScrollPane searchMenu;
    public ScrollPane viewMenu;
    public ScrollPane addMenu;

    public SuppliersViewController(ApplicationModel model) {
        super(model);

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) updateSuppliersTable();
        });
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

    private void activateControl(@NotNull Control pane) {
        pane.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onEdit() {
        activateControl(viewMenu);
    }

    @Override
    public void onAdd() {
        activateControl(addMenu);
    }

    public void onCancelAdd() {

    }

    public void onConfirmAdd() {

    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateControl(searchMenu);
    }
}
