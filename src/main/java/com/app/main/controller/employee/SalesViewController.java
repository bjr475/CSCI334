package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class SalesViewController extends AChildEmployeeEditorActionViewController {

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

    public SalesViewController(ApplicationModel model) {
        super(model);
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {
        boolean state = permissions.isModifyCustomer() || permissions.isCreateCustomer();
        addClubStartDate.setEditable(state);
        addAddressController.setEditable(state);
        setEditable(
                state,
                addSubjects, addTypes
        );
        setEditable(
                state,
                addItemButton, newCustomer, addExistingCredit, otherDiscount, viewOtherDiscount, clubMember
        );
        setEditable(
                state,
                viewOtherDiscountAmount, quantityView, addEmail, addFirstName, addLastName, addCredit,
                otherDiscountAmount
        );
        setEditable(
                state,
                itemTable, confirmItemTable
        );
    }

    @Override
    protected void setAdminEditable() {
        addClubStartDate.setEditable(true);
        addAddressController.setEditable(true);
        setEditable(
                true,
                addSubjects, addTypes
        );
        setEditable(
                true,
                addItemButton, newCustomer, addExistingCredit, otherDiscount, viewOtherDiscount, clubMember
        );
        setEditable(
                true,
                viewOtherDiscountAmount, quantityView, addEmail, addFirstName, addLastName, addCredit,
                otherDiscountAmount
        );
        setEditable(
                true,
                itemTable, confirmItemTable
        );
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
