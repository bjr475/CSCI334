package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class CustomersViewController extends AChildEmployeeViewController implements IEditorActionItem {
    private static final Logger logger = LogManager.getLogger(CustomersViewController.class.getName());

    /* Tool Drawer */
    public JFXDrawer toolDrawer;

    public TableView customersTable;

    /* Edit Control */
    public ScrollPane editMenu;
    public TextField editEmail;
    public TextField editCustomerID;
    public TextField editFirstName;
    public TextField editLastName;
    public CheckBox editExistingCredit;
    public TextField editCredit;
    public CheckBox editClubMember;
    public DatePicker editClubStartDate;
    public ListView<String> editSubjects;
    public ListView<String> editTypes;

    @FXML
    public AddressViewController editAddressController;

    /* Add Control */
    public ScrollPane addMenu;
    public TextField addEmail;
    public TextField addCustomerID;
    public TextField addFirstName;
    public TextField addLastName;
    public CheckBox addExistingCredit;
    public TextField addCredit;
    public CheckBox clubMember;
    public DatePicker addClubStartDate;
    public ListView<String> addSubjects;
    public ListView<String> addTypes;

    public EmployeeModel employeeModel;

    @FXML
    public AddressViewController addAddressController;
    //public ChoiceBox<String> addAddress;

    /* Filter Control */
    public ScrollPane filterMenu;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

//    /* Catalogue Table */
//    public TableView<CustomerModel> catalogueTable;
//
//    /* Add and Edit Values */
//    private ObjectProperty<CustomerModel> currentAddItem;
//    private ObjectProperty<CustomerModel> currentEditableItem;

    private void setEditable(boolean state) {
        editEmail.setEditable(state);
        editFirstName.setEditable(state);
        editLastName.setEditable(state);
        editAddressController.setEditable(state);
        editExistingCredit.setDisable(state);
        editCredit.setEditable(state);
        editClubMember.setDisable(state);
        editClubStartDate.setEditable(state);
        editSubjects.setEditable(state);
        editTypes.setEditable(state);

        addEmail.setEditable(state);
        addCustomerID.setEditable(state);
        addFirstName.setEditable(state);
        addLastName.setEditable(state);
        addExistingCredit.setDisable(state);
        addCredit.setEditable(state);
        clubMember.setDisable(state);
        addClubStartDate.setEditable(state);
        addSubjects.setEditable(state);
        addTypes.setEditable(state);
    }

    public CustomersViewController(ApplicationModel model) {
        super(model);

        model.currentUserProperty().addListener(new ChangeListener<AUserModel>() {
            @Override
            public void changed(ObservableValue<? extends AUserModel> observable, AUserModel oldValue, AUserModel newValue) {
                if (newValue != null) {
                    if (newValue.getUserType() == AUserModel.UserType.EMPLOYEE) {
                        EmployeeModel employee = (EmployeeModel) newValue;
                        EmployeePermissions permissions = employee.getPermissions();
                        boolean canModify = permissions.isModifyCustomer() || permissions.isCreateCustomer();
                        setEditable(canModify);
                    } else {
                        // admin
                        setEditable(true);
                    }
                }
            }
        });
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        addTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addSubjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addTypes.setOnMouseClicked(event -> {

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

    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
    }

    public void onConfirmAdd() {
        ObservableList<String> selectedTypes = addTypes.getSelectionModel().getSelectedItems();
        for (String item : selectedTypes) {
            logger.info("Item {} has been selected", item);
        }
        ObservableList<String> selectedSubjects = addSubjects.getSelectionModel().getSelectedItems();
        for (String item : selectedSubjects) {
            logger.info("Item {} has been selected", item);
        }
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
