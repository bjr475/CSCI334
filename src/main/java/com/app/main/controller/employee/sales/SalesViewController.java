package com.app.main.controller.employee.sales;

import com.app.database.Database;
import com.app.main.controller.employee.AChildEmployeeEditorActionViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.customer.CustomerModel;
import com.app.main.model.sales.SaleItemModel;
import com.app.main.model.sales.SaleModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class SalesViewController extends AChildEmployeeEditorActionViewController {
    private static final Logger logger = LogManager.getLogger(SalesViewController.class.getName());

    public JFXDrawer toolDrawer;
    public TableView<SaleModel> sales;
    public StackPane parentView;
    public ScrollPane searchMenu;

    /*       Edit menu        */
    public ScrollPane editMenu;
    public GridPane updateSale;
    @SuppressWarnings("WeakerAccess")
    public UpdateSaleViewController updateSaleController;
    public JFXDialog refundDialog;

    /*       Add menu         */
    public ScrollPane addMenu;
    public GridPane saleItem;
    public GridPane saleCustomer;
    public GridPane saleConfirm;
    @SuppressWarnings("WeakerAccess")
    public NewSaleItemsViewController saleItemController;
    @SuppressWarnings("WeakerAccess")
    public NewSaleCustomerViewController saleCustomerController;
    @SuppressWarnings("WeakerAccess")
    public NewSaleConfirmViewController saleConfirmController;

    public SalesViewController(ApplicationModel model) {
        super(model);

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshSalesTable();
            }
        });
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {
//        boolean state = permissions.isModifyCustomer() || permissions.isCreateCustomer();
    }

    @Override
    protected void setAdminEditable() {
    }

    @FXML
    public void initialize() {
        SalesUtil.buildSalesTable(sales, this::openEdit);
        toolDrawer.setDefaultDrawerSize(600);
        saleItemController.setOwner(this);
        saleConfirmController.setOwner(this);
        saleCustomerController.setOwner(this);
        updateSaleController.setOwner(this);

        nextSalePage(saleItem);
    }

    private void refreshSalesTable() {
        sales.setItems(FXCollections.observableArrayList(Database.INSTANCE.getSales().getSales()));
        sales.refresh();
    }

    private void activateView(@NotNull Control view) {
        view.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    private void openEdit(SaleModel item) {
        if (item != null) {
            updateSaleController.setSale(item);
            activateView(editMenu);
        }
    }

    @Override
    public void onEdit() {
        openEdit(sales.getSelectionModel().getSelectedItem());
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

    private void nextSalePage(@NotNull GridPane page) {
        page.toFront();
        addMenu.setVvalue(0);
    }

    private void gotoCustomerPage() {
        nextSalePage(saleCustomer);
    }

    private void gotoConfirmPage() {
        saleConfirmController.setItems(saleItemController.getItems());
        nextSalePage(saleConfirm);
    }

    void newSaleCancel() {
        nextSalePage(saleItem);
    }

    void newSaleItemsNext() {
        CustomerModel model = saleItemController.getSelectedCustomer();
        if (model != null) {
            if (model.getId() == 0) gotoCustomerPage();
            else gotoConfirmPage();
        }
    }

    void newSaleCustomerBack() {
        nextSalePage(saleItem);
    }

    void newSaleCustomerNext() {
        gotoConfirmPage();
    }

    void newSaleConfirmBack() {
        CustomerModel model = saleItemController.getSelectedCustomer();
        if (model != null) {
            if (model.getId() == 0) gotoCustomerPage();
            else nextSalePage(saleItem);
        }
    }

    void newSaleConfirm() {
        boolean nc = saleItemController.getSelectedCustomer().getId() == 0;
        CustomerModel customer = nc ? saleCustomerController.getCustomer() : saleItemController.getSelectedCustomer();
        ObservableList<SaleItemModel> saleItems = saleItemController.getItems();
        double finalPrice = saleConfirmController.getSaleTotal();

        int customerId = customer.getId();
        if (customerId == 0) customerId = Database.INSTANCE.getCustomer().insertCustomer(customer);
        if (customerId == -1) {
            logger.fatal("Failed to create new customer: {}", customer);
        } else {
            Database.INSTANCE.getSales().insertSale(customerId, getModel().getCurrentUser().getId(), finalPrice, saleItems);
            refreshSalesTable();
        }
        saleItemController.clearItems();
        toolDrawer.close();
        nextSalePage(saleItem);
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }

    void refundSale() {
        refundDialog.show(parentView);
    }

    public void cancelRefund() {
        refundDialog.close();
    }

    public void performRefund() {
        SaleModel model = sales.getSelectionModel().getSelectedItem();
        if (!model.isRefunded()) {
            model.setRefunded(true);
            Database.INSTANCE.getSales().updateSale(model);
            refreshSalesTable();
            openEdit(model);
            refundDialog.close();
            CustomerModel customer = model.getCustomer();
            customer.setCreditLine(customer.getCreditLine() + model.getTotal());
            Database.INSTANCE.getCustomer().updateCustomer(customer);
        }
    }
}
