package com.app.main.controller.employee;

import com.app.main.Util;
import com.app.main.controller.AChildMainViewController;
import com.app.main.controller.employee.catalogue.CatalogueViewController;
import com.app.main.controller.employee.manager.ManageEmployeesViewController;
import com.app.main.controller.employee.manager.ManageStoresViewController;
import com.app.main.controller.employee.sales.SalesViewController;
import com.app.main.controller.employee.search.SearchViewController;
import com.app.main.controller.employee.supplier.SuppliersViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.customer.CustomerModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class EmployeeViewController extends AChildMainViewController {
    /*Hamburger menu*/
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public CatalogueViewController catalogueController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public CustomersViewController customersController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public SalesViewController salesController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public SuppliersViewController suppliersController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public SettingsViewController settingsController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public ManageEmployeesViewController manageEmployeesController;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public ManageStoresViewController manageStoresController;

    @FXML
    public Pane cataloguePane;

    @FXML
    public Pane customersPane;

    @FXML
    public Pane salesPane;

    @FXML
    public Pane suppliersPane;

    @FXML
    public Pane settingsPane;

    @FXML
    public VBox employeesPane;

    @FXML
    public VBox storesPane;

    public HBox toolbarItems;
    public Label titleLabel;
    public JFXButton editButton;
    public JFXButton addButton;
    public JFXButton searchButton;
    public JFXButton manageStoresBtn;
    public JFXButton manageEmployeesBtn;
    public JFXButton customerBtn;
    public JFXButton salesBtn;
    public VBox searchPane;
    public SearchViewController searchSystemController;

    private ObjectProperty<IEditorActionItem> currentView;

    public EmployeeViewController(ApplicationModel model) {
        super(model);
        currentView = new SimpleObjectProperty<>(null);
        currentView.addListener((observableValue, oldValue, newValue) -> {
            toolbarItems.setVisible(newValue != null && newValue.hasButtons());
            if (oldValue != null) {
                editButton.disableProperty().unbind();
                addButton.disableProperty().unbind();
            }
            if (newValue != null) {
                editButton.disableProperty().bind(Bindings.not(newValue.editProperty()));
                addButton.disableProperty().bind(Bindings.not(newValue.addProperty()));
            }
        });


        model.currentUserProperty().addListener(this::onUserChanged);
    }

    @FXML
    private void initialize() {
        Util.prepareDrawer(mainDrawer, mainMenu);

        catalogueController.setOwner(this);
        customersController.setOwner(this);
        salesController.setOwner(this);
        suppliersController.setOwner(this);
        settingsController.setOwner(this);
        manageEmployeesController.setOwner(this);
        manageStoresController.setOwner(this);
        searchSystemController.setOwner(this);

        setCurrentView(catalogueController, cataloguePane);
    }

    private void setAdminPermissions() {
        manageEmployeesBtn.setDisable(false);
        manageStoresBtn.setDisable(false);
        salesBtn.setDisable(false);
        customerBtn.setDisable(false);

//        addButton.setDisable(false);
//        editButton.setDisable(false);
    }

    private void setEmployeePermissions(@NotNull EmployeePermissions permissions) {
        manageEmployeesBtn.setDisable(permissions.getManageEmployee());
        manageStoresBtn.setDisable(false);
        salesBtn.setDisable(permissions.isViewSale());
        customerBtn.setDisable(permissions.isViewCustomer());
//        addButton.setDisable(!(permissions.isCreateCustomer() || permissions.isCreateItem() || permissions.isCreateSale()));
//        editButton.setDisable(!(permissions.isModifyItem() || permissions.isModifyCustomer() || permissions.isModifySale()));
    }

    private void onUserChanged(@SuppressWarnings("unused") ObservableValue<? extends AUserModel> observable, AUserModel oldValue, AUserModel newValue) {
        titleLabel.setText("Tim's Hobby Shop");
        if (newValue != null) {
            switch (newValue.getUserType()) {
                case ADMIN:
                    titleLabel.setText("Tim's Hobby Shop - Admin");
                    setAdminPermissions();
                    break;
                case EMPLOYEE:
                    titleLabel.setText("Tim's Hobby Shop - Employee");
                    setEmployeePermissions(((EmployeeModel) newValue).getPermissions());
                    break;
            }
        }
        if (oldValue != null) mainDrawer.close();
    }

    private void setCurrentView(IEditorActionItem item, @NotNull Pane view) {
        currentView.set(item);
        view.toFront();
    }

    @FXML
    public void gotoCatalogue() {
        setCurrentView(catalogueController, cataloguePane);
    }

    @FXML
    protected void gotoCustomers() {
        setCurrentView(customersController, customersPane);
    }

    @FXML
    protected void gotoSales() {
        setCurrentView(salesController, salesPane);
    }

    @FXML
    protected void gotoSuppliers() {
        setCurrentView(suppliersController, suppliersPane);
    }

    @FXML
    protected void gotoSettings() {
        setCurrentView(null, settingsPane);
    }

    @FXML
    protected void gotoManageStores() {
        setCurrentView(manageStoresController, storesPane);
    }

    @FXML
    protected void gotoManageEmployees() {
        setCurrentView(manageEmployeesController, employeesPane);
    }

    public void logout() {
        getOwner().logout();
    }

    public void onEdit() {
        IEditorActionItem item = this.currentView.get();
        if (item != null) item.onEdit();
    }

    public void onAdd() {
        IEditorActionItem item = this.currentView.get();
        if (item != null) item.onAdd();
    }

    public void onSearch() {
        searchPane.toFront();
        searchSystemController.search();
    }

    public void gotoCustomer(CustomerModel item) {
        customersPane.toFront();
        customersController.highlightItem(item);
    }

    public void gotoCatalogue(CatalogueItemModel item) {
        cataloguePane.toFront();
        catalogueController.highlightItem(item);
    }
}
