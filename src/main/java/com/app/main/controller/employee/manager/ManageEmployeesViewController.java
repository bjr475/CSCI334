package com.app.main.controller.employee.manager;

import com.app.database.Database;
import com.app.main.controller.employee.AChildEmployeeEditorActionViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeTable;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class ManageEmployeesViewController extends AChildEmployeeEditorActionViewController {
    public JFXDrawer toolDrawer;

    /* Add Menu */
    public ScrollPane addMenu;
    public TextField addDisplayName;
    public TextField addFirstName;
    public TextField addContact;
    public TextField addStore;
    public ChoiceBox<String> addPosition;
    public JFXToggleButton addPermItemAdd;
    public JFXToggleButton addPermItemModify;
    public JFXToggleButton addPermSaleView;
    public JFXToggleButton addPermSaleAdd;
    public JFXToggleButton addPermSaleModify;
    public JFXToggleButton addPermCustomerView;
    public JFXToggleButton addPermCustomerAdd;
    public JFXToggleButton addPermCustomerModify;
    public JFXToggleButton addPermSupplierManage;
    public JFXToggleButton addPermEmployeeManage;
    /* Edit Menu */
    public TableView<EmployeeTable> employeeTableView;
    public ScrollPane editMenu;
    public TextField editEmployeeId;
    public TextField editDisplayName;
    public TextField editFirstName;
    public TextField editContact;
    public TextField editStore;
    public ChoiceBox<String> editPosition;
    public JFXToggleButton editPermItemAdd;
    public JFXToggleButton editPermItemModify;
    public JFXToggleButton editPermSaleView;
    public JFXToggleButton editPermSaleAdd;
    public JFXToggleButton editPermSaleModify;
    public JFXToggleButton editPermCustomerView;
    public JFXToggleButton editPermCustomerAdd;
    public JFXToggleButton editPermCustomerModify;
    public JFXToggleButton editPermSupplierManage;
    public JFXToggleButton editPermEmployeeManage;
    private ObjectProperty<EmployeeTable> addEmployee;
    private boolean addEmployeeNew = false;
    private ObjectProperty<EmployeeTable> editEmployee;
    private boolean editEmployeeNew = false;

    public ManageEmployeesViewController(ApplicationModel model) {
        super(model);

        editEmployee = new SimpleObjectProperty<>(null);
        editEmployee.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                editEmployeeId.setText("-- ID --");
                editDisplayName.textProperty().unbindBidirectional(oldValue.displayNameProperty());
                editFirstName.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                editContact.textProperty().unbindBidirectional(oldValue.contactProperty());
                editStore.textProperty().unbindBidirectional(oldValue.storeProperty());
                editPosition.valueProperty().unbindBidirectional(oldValue.storeProperty());

                editPermItemAdd.selectedProperty().unbindBidirectional(oldValue.createItemProperty());
                editPermItemModify.selectedProperty().unbindBidirectional(oldValue.modifyItemProperty());
                editPermSaleView.selectedProperty().unbindBidirectional(oldValue.viewSaleProperty());
                editPermSaleAdd.selectedProperty().unbindBidirectional(oldValue.createSaleProperty());
                editPermSaleModify.selectedProperty().unbindBidirectional(oldValue.modifySaleProperty());
                editPermCustomerView.selectedProperty().unbindBidirectional(oldValue.viewCustomerProperty());
                editPermCustomerAdd.selectedProperty().unbindBidirectional(oldValue.createCustomerProperty());
                editPermCustomerModify.selectedProperty().unbindBidirectional(oldValue.modifyCustomerProperty());
                editPermSupplierManage.selectedProperty().unbindBidirectional(oldValue.manageSupplierProperty());
                editPermEmployeeManage.selectedProperty().unbindBidirectional(oldValue.manageEmployeeProperty());
                editEmployeeNew = false;
            }

            if (newValue != null) {
                editEmployeeId.setText(String.format("%d", newValue.getEmployeeId()));
                editDisplayName.textProperty().bindBidirectional(newValue.displayNameProperty());
                editFirstName.textProperty().bindBidirectional(newValue.firstNameProperty());
                editContact.textProperty().bindBidirectional(newValue.contactProperty());
                editStore.textProperty().bindBidirectional(newValue.storeProperty());
                editPosition.valueProperty().bindBidirectional(newValue.positionProperty());

                editPermItemAdd.selectedProperty().bindBidirectional(newValue.createItemProperty());
                editPermItemModify.selectedProperty().bindBidirectional(newValue.modifyItemProperty());
                editPermSaleView.selectedProperty().bindBidirectional(newValue.viewSaleProperty());
                editPermSaleAdd.selectedProperty().bindBidirectional(newValue.createSaleProperty());
                editPermSaleModify.selectedProperty().bindBidirectional(newValue.modifySaleProperty());
                editPermCustomerView.selectedProperty().bindBidirectional(newValue.viewCustomerProperty());
                editPermCustomerAdd.selectedProperty().bindBidirectional(newValue.createCustomerProperty());
                editPermCustomerModify.selectedProperty().bindBidirectional(newValue.modifyCustomerProperty());
                editPermSupplierManage.selectedProperty().bindBidirectional(newValue.manageSupplierProperty());
                editPermEmployeeManage.selectedProperty().bindBidirectional(newValue.manageEmployeeProperty());
                editEmployeeNew = true;
            }
        });

        addEmployee = new SimpleObjectProperty<>(null);
        addEmployee.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addDisplayName.textProperty().unbindBidirectional(oldValue.displayNameProperty());
                addFirstName.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                addContact.textProperty().unbindBidirectional(oldValue.contactProperty());
                addStore.textProperty().unbindBidirectional(oldValue.storeProperty());
                addPosition.valueProperty().unbindBidirectional(oldValue.storeProperty());

                addPermItemAdd.selectedProperty().unbindBidirectional(oldValue.createItemProperty());
                addPermItemModify.selectedProperty().unbindBidirectional(oldValue.modifyItemProperty());
                addPermSaleView.selectedProperty().unbindBidirectional(oldValue.viewSaleProperty());
                addPermSaleAdd.selectedProperty().unbindBidirectional(oldValue.createSaleProperty());
                addPermSaleModify.selectedProperty().unbindBidirectional(oldValue.modifySaleProperty());
                addPermCustomerView.selectedProperty().unbindBidirectional(oldValue.viewCustomerProperty());
                addPermCustomerAdd.selectedProperty().unbindBidirectional(oldValue.createCustomerProperty());
                addPermCustomerModify.selectedProperty().unbindBidirectional(oldValue.modifyCustomerProperty());
                addPermSupplierManage.selectedProperty().unbindBidirectional(oldValue.manageSupplierProperty());
                addPermEmployeeManage.selectedProperty().unbindBidirectional(oldValue.manageEmployeeProperty());
                addEmployeeNew = false;
            }

            if (newValue != null) {
                addDisplayName.textProperty().bindBidirectional(newValue.displayNameProperty());
                addFirstName.textProperty().bindBidirectional(newValue.firstNameProperty());
                addContact.textProperty().bindBidirectional(newValue.contactProperty());
                addStore.textProperty().bindBidirectional(newValue.storeProperty());
                addPosition.valueProperty().bindBidirectional(newValue.positionProperty());

                addPermItemAdd.selectedProperty().bindBidirectional(newValue.createItemProperty());
                addPermItemModify.selectedProperty().bindBidirectional(newValue.modifyItemProperty());
                addPermSaleView.selectedProperty().bindBidirectional(newValue.viewSaleProperty());
                addPermSaleAdd.selectedProperty().bindBidirectional(newValue.createSaleProperty());
                addPermSaleModify.selectedProperty().bindBidirectional(newValue.modifySaleProperty());
                addPermCustomerView.selectedProperty().bindBidirectional(newValue.viewCustomerProperty());
                addPermCustomerAdd.selectedProperty().bindBidirectional(newValue.createCustomerProperty());
                addPermCustomerModify.selectedProperty().bindBidirectional(newValue.modifyCustomerProperty());
                addPermSupplierManage.selectedProperty().bindBidirectional(newValue.manageSupplierProperty());
                addPermEmployeeManage.selectedProperty().bindBidirectional(newValue.manageEmployeeProperty());
                addEmployeeNew = true;
            }
        });

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && (newValue.getUserType() == AUserModel.UserType.ADMIN)) refreshTable();
        });
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {

    }

    @Override
    protected void setAdminEditable() {

    }

    private void buildEmployeeTable() {
        TableColumn<EmployeeTable, Number> employeeIdColumn = new TableColumn<>("ID");
        employeeIdColumn.setCellValueFactory(param -> param.getValue().employeeIdProperty());
        employeeTableView.getColumns().add(employeeIdColumn);

        TableColumn<EmployeeTable, String> displayNameColumn = new TableColumn<>("Display Name");
        displayNameColumn.setCellValueFactory(param -> param.getValue().displayNameProperty());
        employeeTableView.getColumns().add(displayNameColumn);

        TableColumn<EmployeeTable, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(param -> param.getValue().firstNameProperty());
        employeeTableView.getColumns().add(firstNameColumn);

        TableColumn<EmployeeTable, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(param -> param.getValue().contactProperty());
        employeeTableView.getColumns().add(contactColumn);

        TableColumn<EmployeeTable, String> storeColumn = new TableColumn<>("Store");
        storeColumn.setCellValueFactory(param -> param.getValue().storeProperty());
        employeeTableView.getColumns().add(storeColumn);

        TableColumn<EmployeeTable, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(param -> param.getValue().positionProperty());
        employeeTableView.getColumns().add(positionColumn);

        TableColumn<EmployeeTable, Boolean> createItemsColumn = new TableColumn<>("Create Items");
        createItemsColumn.setCellValueFactory(param -> param.getValue().createItemProperty());
        employeeTableView.getColumns().add(createItemsColumn);

        TableColumn<EmployeeTable, Boolean> modifyItemsColumn = new TableColumn<>("Modify Items");
        modifyItemsColumn.setCellValueFactory(param -> param.getValue().modifyItemProperty());
        employeeTableView.getColumns().add(modifyItemsColumn);

        TableColumn<EmployeeTable, Boolean> viewSaleColumn = new TableColumn<>("View Sales");
        viewSaleColumn.setCellValueFactory(param -> param.getValue().viewSaleProperty());
        employeeTableView.getColumns().add(viewSaleColumn);

        TableColumn<EmployeeTable, Boolean> createSaleColumn = new TableColumn<>("Create Sales");
        createSaleColumn.setCellValueFactory(param -> param.getValue().createSaleProperty());
        employeeTableView.getColumns().add(createSaleColumn);

        TableColumn<EmployeeTable, Boolean> modifySaleColumn = new TableColumn<>("Modify Sales");
        modifySaleColumn.setCellValueFactory(param -> param.getValue().modifySaleProperty());
        employeeTableView.getColumns().add(modifySaleColumn);

        TableColumn<EmployeeTable, Boolean> viewCustomerColumn = new TableColumn<>("View Customers");
        viewCustomerColumn.setCellValueFactory(param -> param.getValue().viewCustomerProperty());
        employeeTableView.getColumns().add(viewCustomerColumn);

        TableColumn<EmployeeTable, Boolean> createCustomerColumn = new TableColumn<>("Create Customers");
        createCustomerColumn.setCellValueFactory(param -> param.getValue().createCustomerProperty());
        employeeTableView.getColumns().add(createCustomerColumn);

        TableColumn<EmployeeTable, Boolean> modifyCustomerColumn = new TableColumn<>("Modify Customers");
        modifyCustomerColumn.setCellValueFactory(param -> param.getValue().modifyCustomerProperty());
        employeeTableView.getColumns().add(modifyCustomerColumn);

        TableColumn<EmployeeTable, Boolean> manageSupplierColumn = new TableColumn<>("Manage Suppliers");
        manageSupplierColumn.setCellValueFactory(param -> param.getValue().manageSupplierProperty());
        employeeTableView.getColumns().add(manageSupplierColumn);

        TableColumn<EmployeeTable, Boolean> modifySupplierColumn = new TableColumn<>("Manage Employees");
        modifySupplierColumn.setCellValueFactory(param -> param.getValue().manageEmployeeProperty());
        employeeTableView.getColumns().add(modifySupplierColumn);

        employeeTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) openEdit(employeeTableView.getSelectionModel().getSelectedItem());
        });
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        buildEmployeeTable();

        editPosition.valueProperty().addListener((observable, oldValue, newValue) -> {
            EmployeeTable employee = editEmployee.get();
            if (employee != null && newValue != null && editEmployeeNew) {
                if (newValue.equals("Manager"))
                    employee.setPermissions(EmployeePermissions.newManagerPermissions());
                if (newValue.equals("Sales Assistant"))
                    employee.setPermissions(EmployeePermissions.newSalesAssitantPermissions());
                if (newValue.equals("Stock Assistant"))
                    employee.setPermissions(EmployeePermissions.newStockAssistantPermissions());
            }
        });

        addPosition.valueProperty().addListener((observable, oldValue, newValue) -> {
            EmployeeTable employee = addEmployee.get();
            if (employee != null && newValue != null && addEmployeeNew) {
                if (newValue.equals("Manager"))
                    employee.setPermissions(EmployeePermissions.newManagerPermissions());
                if (newValue.equals("Sales Assistant"))
                    employee.setPermissions(EmployeePermissions.newSalesAssitantPermissions());
                if (newValue.equals("Stock Assistant"))
                    employee.setPermissions(EmployeePermissions.newStockAssistantPermissions());
            }
        });
    }

    private void activateView(@NotNull ScrollPane view) {
        view.toFront();
        toolDrawer.open();
    }

    private void openEdit(EmployeeTable item) {
        if (item != null) {
            editEmployee.set(new EmployeeTable(item));
            activateView(editMenu);
        }
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    private void refreshTable() {
        employeeTableView.setItems(FXCollections.observableArrayList(
                Database.INSTANCE.getUser().getEmployeeTable()
        ));
        employeeTableView.refresh();
    }

    @Override
    public void onEdit() {
        EmployeeTable item = employeeTableView.getSelectionModel().getSelectedItem();
        if (item != null) openEdit(item);
    }

    public void cancelEdit() {
        toolDrawer.close();
        editEmployee.set(null);
    }

    public void confirmEdit() {
        toolDrawer.close();
        Database.INSTANCE.getUser().saveEmployeeTable(editEmployee.get());
        editEmployee.set(null);
        refreshTable();
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
        addEmployee.set(new EmployeeTable(0));
    }

    public void cancelAdd(ActionEvent event) {
        addEmployee.set(new EmployeeTable(0));
        toolDrawer.close();
    }

    public void confirmAdd(ActionEvent event) {
        toolDrawer.close();
        Database.INSTANCE.getUser().insertEmployeeTable(addEmployee.get());
        addEmployee.set(null);
        refreshTable();
    }
}

