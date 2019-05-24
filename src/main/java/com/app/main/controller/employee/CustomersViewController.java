package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.CustomerModel;
import com.app.main.model.SubjectListCellModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    public TextField editCredit;
    public CheckBox editClubMember;
    public DatePicker editClubStartDate;
    public ListView<SubjectListCellModel> editSubjects;
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
    public DatePicker addClubStartDate;
    public ListView<SubjectListCellModel> addSubjects;
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
//    private ObjectProperty<CustomerModel> currentEditableItem;

    public CustomersViewController(ApplicationModel model) {
        super(model);

        currentAddItem = new SimpleObjectProperty<>(new CustomerModel());

        currentAddItem.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
                addFirstName.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                addLastName.textProperty().unbindBidirectional(oldValue.lastNameProperty());
                addAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                addClubMember.selectedProperty().unbindBidirectional(oldValue.clubMemberProperty());
                addSubjects.itemsProperty().unbindBidirectional(oldValue.subjectAreasProperty());
                addTypes.itemsProperty().unbindBidirectional(oldValue.modelTypesProperty());
            }
            if (newValue != null) {
                addEmail.textProperty().bindBidirectional(newValue.emailProperty());
                addFirstName.textProperty().bindBidirectional(newValue.firstNameProperty());
                addLastName.textProperty().bindBidirectional(newValue.lastNameProperty());
                addAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
                addClubMember.selectedProperty().bindBidirectional(newValue.clubMemberProperty());
                addSubjects.itemsProperty().bindBidirectional(newValue.subjectAreasProperty());
                addTypes.itemsProperty().bindBidirectional(newValue.modelTypesProperty());
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
                editEmail, editFirstName, editLastName, editCredit
        );
        setEditable(
                state,
                editExistingCredit, editClubMember, editClubMember
        );
        setEditable(
                state,
                editSubjects, editTypes
        );
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

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        addTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addSubjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        addCredit.valueProperty().addListener((observable, oldValue, newValue) -> currentAddItem.get().setCreditLine(newValue));
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
        AUserModel currentUser = getModel().getCurrentUser();
        if (currentUser != null && currentUser.getUserType() == AUserModel.UserType.EMPLOYEE) {
            EmployeeModel employeeModel = (EmployeeModel) currentUser;
            EmployeePermissions permissions = employeeModel.getPermissions();
            if (!permissions.isModifyCustomer()) return;
        }
        activateView(editMenu);
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
    }

    public void onConfirmAdd() {
        ObservableList<String> selectedTypes = addTypes.getSelectionModel().getSelectedItems();
        for (String item : selectedTypes) {
            logger.info("Item {} has been selected", item);
        }
        ObservableList<SubjectListCellModel> selectedSubjects = addSubjects.getSelectionModel().getSelectedItems();
        for (SubjectListCellModel item : selectedSubjects) {
            logger.info("Item {} has been selected", item);
        }
    }

    @Override
    public void onFilter() {
//        activateView(filterMenu);
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
