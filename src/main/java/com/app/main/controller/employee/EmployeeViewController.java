package com.app.main.controller.employee;

import com.app.main.controller.AChildMainViewController;
import com.app.main.controller.ControllerUtil;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class EmployeeViewController extends AChildMainViewController {
    /*Hamburger menu*/
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public CatalogueViewController catalogueController;

    @FXML
    public CustomersViewController customersController;

    @FXML
    public SalesViewController salesController;

    @FXML
    public SuppliersViewController suppliersController;

    @FXML
    public SettingsViewController settingsController;
    
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

    public HBox toolbarItems;

    /*JFXButtons*/
    private ObjectProperty<IEditorActionItem> currentView;

    public EmployeeViewController(ApplicationModel model) {
        super(model);
        currentView = new SimpleObjectProperty<>(null);
        currentView.addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) toolbarItems.setVisible(newValue.hasButtons());
            else toolbarItems.setVisible(true);
        });
    }

    @FXML
    private void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        catalogueController.setOwner(this);
        customersController.setOwner(this);
        salesController.setOwner(this);
        suppliersController.setOwner(this);
        settingsController.setOwner(this);

        setCurrentView(catalogueController, cataloguePane);
    }

    private void setCurrentView(IEditorActionItem item, @NotNull Pane view) {
        currentView.set(item);
        view.toFront();
    }

    @FXML
    protected void gotoCatalogue() {
        setCurrentView(catalogueController, cataloguePane);
    }

    @FXML
    protected void gotoCustomers() {
        setCurrentView(null, customersPane);
    }

    @FXML
    protected void gotoSales() {
        setCurrentView(null, salesPane);
    }

    @FXML
    protected void gotoSuppliers() {
        setCurrentView(null, suppliersPane);
    }

    @FXML
    protected void gotoSettings() {
        setCurrentView(null, settingsPane);
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

    public void onFilter() {
        IEditorActionItem item = this.currentView.get();
        if (item != null) item.onFilter();
    }

    public void onSearch() {
        IEditorActionItem item = this.currentView.get();
        if (item != null) item.onSearch();
    }
}