package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainFrameViewController extends AViewController {

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
    public LandingLoginViewController landingLoginController;

    @FXML
    public AnchorPane landingLoginPane, cataloguePane, customersPane, salesPane, suppliersPane, settingsPane;



    public MainFrameViewController(ApplicationModel model) {
        super(model);
    }


    @FXML
    private void initialize() {

        catalogueController.setOwner(this);
        customersController.setOwner(this);
        salesController.setOwner(this);
        suppliersController.setOwner(this);
        settingsController.setOwner(this);
//      landingLoginController.setOwner(this);

        catalogueController.customer.setOnMouseClicked(event -> customersPane.toFront());
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
        settingsController.sales.setOnMouseClicked(event -> suppliersPane.toFront());
    }

}
