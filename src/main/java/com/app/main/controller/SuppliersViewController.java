package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class SuppliersViewController extends AChildMainViewController{
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public JFXButton catalogue, customer, sales, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    public LandingLoginViewController landingloginController;

    @FXML
    public CatalogueViewController catalogController;

    @FXML
    public CustomersViewController customersController;

    @FXML
    public SalesViewController salesController;

    @FXML
    public BorderPane modMenu, viewMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public TableView tableView;

    @FXML
    public JFXDialog contactDetailsDialog, addItemDialog;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        editButton.setOnMouseClicked(event -> modMenu.toFront());
        addButton.setOnMouseClicked(event -> modMenu.toFront());
    }

    /*@FXML
    protected void onSave(ActionEvent event) {
        viewMenu.toFront();
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }*/

    /*@FXML
    protected void onAddContact(ActionEvent event) {
        contactDetailsDialog.toFront();
    }*/

    public void confirmAddContactDetails() {

        contactDetailsDialog.close();
    }

    public void confirmAddItemDialog() {

        addItemDialog.close();
    }

    public void showViewMenu() {

        viewMenu.toFront();
    }
}
