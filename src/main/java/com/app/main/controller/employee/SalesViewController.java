package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class SalesViewController extends AChildEmployeeViewController implements IEditorActionItem {

    public JFXDrawer toolDrawer;
    public TableView tableView;
    public ScrollPane searchMenu;

    /*       Edit menu        */
    public ScrollPane editMenu;
    public TableView editItemTable;
    public TextField editCustomerID;
    public CheckBox viewOtherDiscount;
    public TextField viewOtherDiscountAmount;
    public Text viewSubtotal;
    public Text viewDiscount;
    public Text viewGST;
    public Text viewTotal;

    /*       Add menu         */
    public ScrollPane addMenu;

    /*Item Grid*/
    public GridPane saleItemGrid;
    public TableView itemTable;
    public TextField itemIDView;
    public TextField quantityView;
    public JFXButton addItemButton;
    public TextField customerID;
    public ToggleButton newCustomer;


    /*Customer Grid*/
    public GridPane saleCustomerGrid;
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
    public AddressViewController addAddressController;

    /*Confirm Grid*/
    public GridPane saleConfirmGrid;
    public TableView confirmItemTable;
    public CheckBox otherDiscount;
    public TextField otherDiscountAmount;
    public Text subtotal;
    public Text discount;
    public Text GST;
    public Text total;


    private void setEditable(boolean state) {
        editCustomerID.setEditable(state);
        viewOtherDiscount.setDisable(!state);
        viewOtherDiscountAmount.setEditable(state);

        itemTable.setEditable(state);
        itemIDView.setEditable(state);
        quantityView.setEditable(state);
        addItemButton.setDisable(!state);
        customerID.setEditable(state);
        newCustomer.setDisable(!state);

        addEmail.setEditable(state);
        addCustomerID.setEditable(state);
        addFirstName.setEditable(state);
        addLastName.setEditable(state);
        addExistingCredit.setDisable(!state);
        addCredit.setEditable(state);
        clubMember.setDisable(!state);
        addClubStartDate.setEditable(state);
        addSubjects.setEditable(state);
        addTypes.setEditable(state);
        addAddressController.setEditable(state);

        saleConfirmGrid.setDisable(!state);
        confirmItemTable.setEditable(state);
        otherDiscount.setDisable(!state);
        otherDiscountAmount.setEditable(state);
    }

    public SalesViewController(ApplicationModel model) {
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

    }

    private void activateView(@NotNull Control view) {
        view.toFront();
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

    public void onCancelEdit() {
        editMenu.setVvalue(0);
        AUserModel currentUser = getModel().getCurrentUser();
        if (currentUser != null && currentUser.getUserType() == AUserModel.UserType.EMPLOYEE) {
            EmployeeModel employeeModel = (EmployeeModel) currentUser;
            EmployeePermissions permissions = employeeModel.getPermissions();
            if (!permissions.isModifySale()) return;
        }
        toolDrawer.close();
    }

    @Override
    public void onAdd() {
        AUserModel currentUser = getModel().getCurrentUser();
        if (currentUser != null && currentUser.getUserType() == AUserModel.UserType.EMPLOYEE) {
            EmployeeModel employeeModel = (EmployeeModel) currentUser;
            EmployeePermissions permissions = employeeModel.getPermissions();
            if (!permissions.isCreateSale()) return;
        }
        activateView(addMenu);
    }

    public void onCancelAdd() {
        addMenu.setVvalue(0);
        toolDrawer.close();
    }

    public void onSaleNext() {
        addMenu.setVvalue(0);
        if (newCustomer.isSelected()) {
            saleCustomerGrid.toFront();
        } else {
            saleConfirmGrid.toFront();
        }
    }

    public void onCustomerNext() {
        addMenu.setVvalue(0);
        saleConfirmGrid.toFront();
    }

    public void onCustomerBack() {
        addMenu.setVvalue(0);
        saleItemGrid.toFront();
    }

    public void onConfirmBack() {
        addMenu.setVvalue(0);
        if (newCustomer.isSelected()) {
            saleCustomerGrid.toFront();
        } else {
            saleItemGrid.toFront();
        }
    }

    public void onConfirmAdd() {

    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}
