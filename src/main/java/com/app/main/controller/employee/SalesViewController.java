package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.SalesModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SalesViewController extends AChildEmployeeEditorActionViewController {

    /* Tool Drawer */
    public JFXDrawer toolDrawer;

    /* Edit Control */
    public ScrollPane editMenu;
    public Text viewSaleID;
    public Text viewSaleDate;
    public TableView editItemTable;
    public TextField editCustomerID;
    public CheckBox viewOtherDiscount;
    public TextField viewOtherDiscountAmount;
    public Text viewSubtotal;
    public Text viewDiscount;
    public Text viewGST;
    public Text viewTotal;

    /* Add Control */
    public ScrollPane addMenu;

    /* Item Grid */
    public GridPane saleItemGrid;
    public TextField saleID;
    public CheckBox pastSaleRecord;
    public DatePicker saleDate;
    public TableView itemTable;
    public TextField itemIDView;
    public TextField quantityView;
    public JFXButton addItemButton;
    public TextField customerID;
    public ToggleButton newCustomer;

    /* Customer Grid */
    public GridPane saleCustomerGrid;
    public TextField addEmail;
    public TextField addCustomerID;
    public TextField addFirstName;
    public TextField addLastName;
    public CheckBox addExistingCredit;
    public Label addCreditLabel;
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
    public ToggleButton discountPercentage;
    public ToggleButton discountAmount;
    public TextField otherDiscountAmount;
    public Text subtotal;
    public Text discount;
    public Text GST;
    public Text total;

    /* Sale Items Dialog */
    public JFXDialog saleItemsDialog;
    public TextField searchItems;
    public TableView selectItemTable;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

    /* Catalogue Table */
    public TableView<SalesModel> salesTable;
    public StackPane parentSalesPane;

    public CatalogueItemModel catalogueModel;

    /* Add and Edit Values */
    private ObjectProperty<SalesModel> currentAddSale;
    private ObjectProperty<SalesModel> currentEditableSale;

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

    private void unbindSaleModelAdd(@Nullable SalesModel sale,
                                    @NotNull TextField saleID, @NotNull DatePicker saleDate,
                                    @NotNull TableView itemTable, @NotNull TextField itemID,
                                    @NotNull TextField quantity, @NotNull TextField customerID,
                                    @NotNull TextField email, @NotNull TextField newCustomerID,
                                    @NotNull TextField firstName, @NotNull TextField addLastName,
                                    @NotNull CheckBox addExistingCredit, @NotNull TextField addCredit,
                                    @NotNull CheckBox clubMember, @NotNull DatePicker addClubStartDate,
                                    @NotNull ListView<String> addSubjects, @NotNull ListView<String> addTypes,
                                    @NotNull AddressViewController addAddressController, @NotNull TableView confirmItemTable,
                                    @NotNull CheckBox otherDiscount, @NotNull TextField otherDiscountAmount) {

        if (sale != null) {

        }
    }

    private void bindSaleModelAdd(@Nullable SalesModel sale,
                                  @NotNull TextField saleID, @NotNull DatePicker saleDate,
                                  @NotNull TableView itemTable, @NotNull TextField itemID,
                                  @NotNull TextField quantity, @NotNull TextField customerID,
                                  @NotNull TextField email, @NotNull TextField newCustomerID,
                                  @NotNull TextField firstName, @NotNull TextField addLastName,
                                  @NotNull CheckBox addExistingCredit, @NotNull TextField addCredit,
                                  @NotNull CheckBox clubMember, @NotNull DatePicker addClubStartDate,
                                  @NotNull ListView<String> addSubjects, @NotNull ListView<String> addTypes,
                                  @NotNull AddressViewController addAddressController, @NotNull TableView confirmItemTable,
                                  @NotNull CheckBox otherDiscount, @NotNull TextField otherDiscountAmount) {

        if (sale != null) {

        }
    }

    private void unbindSaleModel(@Nullable SalesModel sale,
                                 @NotNull TextField saleID, @NotNull DatePicker saleDate,
                                 @NotNull TableView itemTable, @NotNull TextField itemID,
                                 @NotNull TextField quantity, @NotNull TextField customerID,
                                 @NotNull TextField email, @NotNull TextField newCustomerID,
                                 @NotNull TextField firstName, @NotNull TextField addLastName,
                                 @NotNull CheckBox addExistingCredit, @NotNull TextField addCredit,
                                 @NotNull CheckBox clubMember, @NotNull DatePicker addClubStartDate,
                                 @NotNull ListView<String> addSubjects, @NotNull ListView<String> addTypes,
                                 @NotNull AddressViewController addAddressController, @NotNull TableView confirmItemTable,
                                 @NotNull CheckBox otherDiscount, @NotNull TextField otherDiscountAmount) {
        if (sale != null) {
            unbindSaleModelAdd(sale, saleID, saleDate,
                    itemTable, itemID, quantity, customerID,
                    email, newCustomerID, firstName, addLastName,
                    addExistingCredit, addCredit, clubMember, addClubStartDate,
                    addSubjects, addTypes, addAddressController,
                    confirmItemTable, otherDiscount, otherDiscountAmount);
        }
    }

    private void bindSaleModel(@Nullable SalesModel sale,
                               @NotNull TextField saleID, @NotNull DatePicker saleDate,
                               @NotNull TableView itemTable, @NotNull TextField itemID,
                               @NotNull TextField quantity, @NotNull TextField customerID,
                               @NotNull TextField email, @NotNull TextField newCustomerID,
                               @NotNull TextField firstName, @NotNull TextField addLastName,
                               @NotNull CheckBox addExistingCredit, @NotNull TextField addCredit,
                               @NotNull CheckBox clubMember, @NotNull DatePicker addClubStartDate,
                               @NotNull ListView<String> addSubjects, @NotNull ListView<String> addTypes,
                               @NotNull AddressViewController addAddressController, @NotNull TableView confirmItemTable,
                               @NotNull CheckBox otherDiscount, @NotNull TextField otherDiscountAmount) {
        if (sale != null) {

            bindSaleModelAdd(sale, saleID, saleDate, itemTable, itemID, quantity, customerID,
                    email, newCustomerID, firstName, addLastName,
                    addExistingCredit, addCredit, clubMember, addClubStartDate,
                    addSubjects, addTypes, addAddressController,
                    confirmItemTable, otherDiscount, otherDiscountAmount);
        }
    }

    private void onUpdateAddItem(@SuppressWarnings("unused") ObservableValue<? extends SalesModel> observable,
                                 SalesModel oldValue, SalesModel newValue) {
        unbindSaleModel(
                oldValue,
                saleID, saleDate, itemTable, itemIDView, quantityView, customerID,
                addEmail, addCustomerID, addFirstName, addLastName,
                addExistingCredit, addCredit, clubMember, addClubStartDate,
                addSubjects, addTypes, addAddressController,
                confirmItemTable, otherDiscount, otherDiscountAmount
        );
        bindSaleModel(
                oldValue,
                saleID, saleDate, itemTable, itemIDView, quantityView, customerID,
                addEmail, addCustomerID, addFirstName, addLastName,
                addExistingCredit, addCredit, clubMember, addClubStartDate,
                addSubjects, addTypes, addAddressController,
                confirmItemTable, otherDiscount, otherDiscountAmount
        );
    }

    public void onUpdateEditItem(@SuppressWarnings("unused") ObservableValue<? extends SalesModel> observable,
                                 SalesModel oldValue, SalesModel newValue) {

    }

    private void userChanged(ObservableValue<? extends AUserModel> observable, AUserModel oldValue, AUserModel newValue) {
        salesTable.setItems(FXCollections.observableArrayList());

    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        ToggleGroup discount = new ToggleGroup();
        discountPercentage.setToggleGroup(discount);
        discountAmount.setToggleGroup(discount);

        pastSaleRecord.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                saleDate.setVisible(pastSaleRecord.isSelected());
            }
        });

        clubMember.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (pastSaleRecord.isSelected())
                    addClubStartDate.setVisible(clubMember.isSelected());
            }
        });

        addExistingCredit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                addCredit.setVisible(addExistingCredit.isSelected());
                addCreditLabel.setVisible(addExistingCredit.isSelected());
            }
        });

        otherDiscount.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                discountPercentage.setVisible(otherDiscount.isSelected());
                discountAmount.setVisible(otherDiscount.isSelected());
                otherDiscountAmount.setVisible(otherDiscount.isSelected());
            }
        });
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

    public void resetSearch() {
        searchWords.setText("");
    }

    public void confirmSearch() {
        salesTable.getItems().stream()
                .filter(item -> item.getSaleID() == searchWords.getText())
                .findAny()
                .ifPresent(item -> {
                    salesTable.getSelectionModel().select(item);
                    salesTable.scrollTo(item);
                });
    }

    public void searchItemTable() {

    }

    public void cancelSaleItemsDialog() {
        saleItemsDialog.close();
    }

    public void saveSaleItemsDialog() {
        saleItemsDialog.close();
    }
}



