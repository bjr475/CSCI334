package com.app.main.controller.employee;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.CustomerModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class CustomersViewController extends AChildEmployeeEditorActionViewController {
    private static final Logger logger = LogManager.getLogger(CustomersViewController.class.getName());

    /* Tool Drawer */
    public JFXDrawer toolDrawer;

    /* Edit Control */
    public ScrollPane editMenu;
    public TextField editEmail;
    public TextField editFirstName;
    public TextField editLastName;
    public CheckBox editExistingCredit;
    public Spinner<Double> editCredit;
    public CheckBox editClubMember;
    public DatePicker editClubStartDate;
    public ListView<String> editSubjects;
    public ListView<String> editTypes;

    @FXML
    public AddressViewController editAddressController;

    /* Add Control */
    public ScrollPane addMenu;
    public TextField addEmail;
    public TextField addFirstName;
    public TextField addLastName;
    public CheckBox addExistingCredit;
    public Spinner<Double> addCredit;
    public CheckBox addClubMember;
    public ListView<String> addSubjects;
    public ListView<String> addTypes;

    @FXML
    public AddressViewController addAddressController;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

    /* Customer Table */
    public TableView<CustomerModel> customersTable;

    /* Add and Edit Values */
    private ObjectProperty<CustomerModel> currentAddItem;
    private ObjectProperty<CustomerModel> currentEditItem;

    public CustomersViewController(ApplicationModel model) {
        super(model);

        currentAddItem = new SimpleObjectProperty<>();
        currentEditItem = new SimpleObjectProperty<>();

        currentAddItem.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
                addFirstName.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                addLastName.textProperty().unbindBidirectional(oldValue.lastNameProperty());
                addAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                addClubMember.selectedProperty().unbindBidirectional(oldValue.clubMemberProperty());
            }
            if (newValue != null) {
                addEmail.textProperty().bindBidirectional(newValue.emailProperty());
                addFirstName.textProperty().bindBidirectional(newValue.firstNameProperty());
                addLastName.textProperty().bindBidirectional(newValue.lastNameProperty());
                addAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                addClubMember.selectedProperty().bindBidirectional(newValue.clubMemberProperty());

                refreshCustomersTable();
            }
        });

        currentEditItem.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                editEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
                editFirstName.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                editLastName.textProperty().unbindBidirectional(oldValue.lastNameProperty());
                editAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                editClubMember.selectedProperty().unbindBidirectional(oldValue.clubMemberProperty());
            }
            if (newValue != null) {
                editEmail.textProperty().bindBidirectional(newValue.emailProperty());
                editFirstName.textProperty().bindBidirectional(newValue.firstNameProperty());
                editLastName.textProperty().bindBidirectional(newValue.lastNameProperty());
                editAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                editClubMember.selectedProperty().bindBidirectional(newValue.clubMemberProperty());
                refreshCustomersTable();
            }
        });
    }

    private void setEditableAdd(boolean state) {
        setEditable(
                state,
                addEmail, addFirstName, addLastName
        );
        setEditable(
                state,
                addExistingCredit, addClubMember
        );
        setEditable(
                state,
                addSubjects, addTypes
        );
        addCredit.setDisable(!state);
        addAddressController.setEditable(state);
    }

    private void setEditableEdit(boolean state) {
        setEditable(
                state,
                editEmail, editFirstName, editLastName
        );
        setEditable(
                state,
                editExistingCredit, editClubMember, editClubMember
        );
        setEditable(
                state,
                editSubjects, editTypes
        );
        editCredit.setDisable(!state);
        editClubStartDate.setEditable(state);
        editAddressController.setEditable(state);
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

    private void refreshCustomersTable() {
        customersTable.setItems(FXCollections.observableArrayList(Database.INSTANCE.getCustomer().getCustomers()));
        customersTable.refresh();
    }

    private void buildCustomersTable() {
        TableColumn<CustomerModel, Number> customerIdColumn = new TableColumn<>("Customer No.");
        customerIdColumn.setCellValueFactory(param -> param.getValue().idProperty());
        customerIdColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CustomerModel, Number> call(TableColumn<CustomerModel, Number> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        if (!empty && item != null) {
                            setGraphic(new Label(Util.formatId(item)));
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                };
            }
        });
        customersTable.getColumns().add(customerIdColumn);

        TableColumn<CustomerModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> param.getValue().emailProperty());
        customersTable.getColumns().add(emailColumn);

        TableColumn<CustomerModel, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(param -> param.getValue().firstNameProperty());
        customersTable.getColumns().add(firstNameColumn);

        TableColumn<CustomerModel, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(param -> param.getValue().lastNameProperty());
        customersTable.getColumns().add(lastNameColumn);

        TableColumn<CustomerModel, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(p -> Bindings.concat(p.getValue().getAddress().format()));
        customersTable.getColumns().add(addressColumn);

        TableColumn<CustomerModel, Number> creditColumn = new TableColumn<>("Credit Line");
        creditColumn.setCellValueFactory(param -> param.getValue().creditLineProperty());
        customersTable.getColumns().add(creditColumn);

        TableColumn<CustomerModel, Boolean> memberColumn = new TableColumn<>("Club Member");
        memberColumn.setCellValueFactory(param -> param.getValue().clubMemberProperty());
        customersTable.getColumns().add(memberColumn);

        customersTable.setRowFactory(param -> {
            TableRow<CustomerModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) openEdit(row.getItem());
            });
            return row;
        });
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        buildCustomersTable();

        currentAddItem.set(new CustomerModel());
        currentEditItem.set(new CustomerModel());

        addTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addSubjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        editTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        editSubjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addCredit.valueProperty().addListener((observable, oldValue, newValue) -> currentAddItem.get().setCreditLine(newValue));
        editCredit.valueProperty().addListener((observable, oldValue, newValue) -> currentEditItem.get().setCreditLine(newValue));
    }

    private void activateView(@NotNull ScrollPane view) {
        view.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    public void openEdit(CustomerModel customer) {
        AUserModel currentUser = getModel().getCurrentUser();
        if (currentUser != null && currentUser.getUserType() == AUserModel.UserType.EMPLOYEE) {
            EmployeeModel employeeModel = (EmployeeModel) currentUser;
            EmployeePermissions permissions = employeeModel.getPermissions();
            if (!permissions.isModifyCustomer()) return;
        }

        if (customer != null) {
            currentEditItem.set(customer);
            editCredit.getValueFactory().setValue(customer.getCreditLine());
            for (String item : customer.getModelTypes()) editTypes.getSelectionModel().select(item);
            for (String item : customer.getSubjectAreas()) editSubjects.getSelectionModel().select(item);
            activateView(editMenu);
        }
    }

    @Override
    public void onEdit() {
        openEdit(customersTable.getSelectionModel().getSelectedItem());
    }

    public void onCancelEdit() {
        toolDrawer.close();
        currentEditItem.set(new CustomerModel());
        editCredit.getValueFactory().setValue(0.0);
        editTypes.getSelectionModel().clearSelection();
        editSubjects.getSelectionModel().clearSelection();
    }

    public void onConfirmEdit() {
        // TODO save edit
        onCancelAdd();
    }

    @Override
    public void onAdd() {
        AUserModel currentUser = getModel().getCurrentUser();
        if (currentUser != null && currentUser.getUserType() == AUserModel.UserType.EMPLOYEE) {
            EmployeeModel employeeModel = (EmployeeModel) currentUser;
            EmployeePermissions permissions = employeeModel.getPermissions();
            if (!permissions.isCreateCustomer()) return;
        }
        activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
        currentAddItem.set(new CustomerModel());
        addCredit.getValueFactory().setValue(0.0);
        addTypes.getSelectionModel().clearSelection();
        addSubjects.getSelectionModel().clearSelection();
    }

    public void onConfirmAdd() {
        currentAddItem.get().setModelTypes(addTypes.getSelectionModel().getSelectedItems());
        currentAddItem.get().setSubjectAreas(addSubjects.getSelectionModel().getSelectedItems());
        Database.INSTANCE.getCustomer().saveCustomer(currentAddItem.get());
        refreshCustomersTable();
        onCancelAdd();
    }

    @Override
    public void onFilter() {
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
