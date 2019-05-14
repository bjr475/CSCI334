package com.app.main.controller;

import com.app.main.controller.employee.AChildEmployeeViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class EmployeeViewController extends AChildMainViewController {
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

    /*Hamburger menu*/
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
    public Pane catalogue, customers, sales, suppliers, settings, logout;

    private AChildEmployeeViewController currentView;


    public EmployeeViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    private void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        catalogueController.setOwner(this);
        customersController.setOwner(this);
        salesController.setOwner(this);
        suppliersController.setOwner(this);
        settingsController.setOwner(this);

//        cataloguePane.toFront();
        currentView = catalogueController;



        /*catalogue.setOnMouseClicked(event -> customersPane.toFront());
        customers.setOnMouseClicked(event -> customersPane.toFront());
        sales.setOnMouseClicked(event -> salesPane.toFront());
        suppliers.setOnMouseClicked(event -> suppliersPane.toFront());
        settingsButton.setOnMouseClicked(event -> settingsPane.toFront());*/
    }

    @FXML
    protected void gotoCatalogue(ActionEvent event) {
        cataloguePane.toFront();
        currentView = catalogueController;
    }

    @FXML
    protected void gotoCustomers(ActionEvent event) {
        customersPane.toFront();
        currentView = customersController;
    }

    @FXML
    protected void gotoSales(ActionEvent event) {
        salesPane.toFront();
        currentView = salesController;
    }

    @FXML
    protected void gotoSuppliers(ActionEvent event) {
        suppliers.toFront();
        currentView = suppliersController;
    }

    @FXML
    protected void gotoSettings(ActionEvent event) {
        settingsPane.toFront();
        currentView = settingsController;
    }

    public void logout() {
        getOwner().logout();
    }

}
