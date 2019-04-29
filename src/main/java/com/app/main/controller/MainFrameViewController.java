package com.app.main.controller;

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
    public AnchorPane cataloguePane, customersPane, salesPane, suppliersPane, settingsPane;



    public MainFrameViewController(ApplicationModel model) {
        super(model);
    }


    @FXML
    private void initialize() {

        catalogueController.setOwner(this);
        customersController.setOwner(this);
        salesController.setOwner(this);
        suppliersController.setOwner(this);

        /*catalogueController.customer.setOnMouseClicked(event -> customersPane.toFront());
        catalogueController.sales.setOnMouseClicked(event -> salesPane.toFront());
        catalogueController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());

        customersController.catalogue.setOnMouseClicked(event -> cataloguePane.toFront());
        customersController.sales.setOnMouseClicked(event -> salesPane.toFront());
        customersController.suppliers.setOnMouseClicked(event -> suppliersPane.toFront());

        suppliersController.catalogue.setOnMouseClicked(event -> cataloguePane.toFront());
        suppliersController.customer.setOnMouseClicked(event -> customersPane.toFront());
        suppliersController.sales.setOnMouseClicked(event -> salesPane.toFront());*/
    }

}
