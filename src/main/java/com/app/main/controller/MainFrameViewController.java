package com.app.main.controller;

import com.app.main.controller.employee.EmployeeViewController;
import com.app.main.controller.landing.LandingViewController;
import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainFrameViewController extends AViewController {

    @FXML
    public LandingViewController landingController;

    @FXML
    public EmployeeViewController employeeController;

    @FXML
    public Pane landingPane, employeePane;


    public MainFrameViewController(ApplicationModel model) {
        super(model);
    }


    @FXML
    private void initialize() {

        landingController.setOwner(this);
        employeeController.setOwner(this);

        employeePane.toFront();


        /*catalogueController.customer.setOnMouseClicked(event -> customersPane.toFront());
        catalogueController.sales.setOnMouseClicked(event -> salesPane.toFront());
        catalogueController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());
        catalogueController.settings.setOnMouseClicked(event -> settingsPane.toFront());

        customersController.catalogue.setOnMouseClicked(event -> cataloguePane.toFront());
        customersController.sales.setOnMouseClicked(event -> salesPane.toFront());
        customersController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());
        customersController.settings.setOnMouseClicked(event -> settingsPane.toFront());

        suppliersController.catalogue.setOnMouseClicked(event -> cataloguePane.toFront());
        suppliersController.customer.setOnMouseClicked(event -> customersPane.toFront());
        suppliersController.sales.setOnMouseClicked(event -> salesPane.toFront());
        suppliersController.settings.setOnMouseClicked(event -> settingsPane.toFront());

        salesController.customer.setOnMouseClicked(event -> customersPane.toFront());
        salesController.catalogue.setOnMouseClicked(event -> salesPane.toFront());
        salesController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());
        salesController.settings.setOnMouseClicked(event -> settingsPane.toFront());

        settingsController.customer.setOnMouseClicked(event -> customersPane.toFront());
        settingsController.catalogue.setOnMouseClicked(event -> salesPane.toFront());
        settingsController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());
        settingsController.sales.setOnMouseClicked(event -> suppliersPane.toFront());*/


    }

    public void logout() {
        employeePane.toBack();
        landingPane.toFront();
    }
}
