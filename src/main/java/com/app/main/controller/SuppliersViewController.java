package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    public BorderPane modMenu, viewMenu, searchMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public ScrollPane sTableView;

    @FXML
    public TableView tableView;

    @FXML
    public JFXDialog addItemDialog;

    @FXML
    public JFXCheckBox creditLineExists;

    @FXML
    public TextField creditLine;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        editButton.setOnMouseClicked(event -> modMenu.toFront());
        addButton.setOnMouseClicked(event -> modMenu.toFront());
        tableView.setOnMouseClicked(event -> viewMenu.toFront());
        searchButton.setOnMouseClicked(event -> searchMenu.toFront());
    }

    @FXML
    protected void onSave(ActionEvent event) {
        viewMenu.toFront();
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }

    @FXML
    protected void onCancel(ActionEvent event) {
        sTableView.toFront();
    }

    @FXML
    protected void onClose(ActionEvent event) {
        sTableView.toFront();
    }

    public void confirmAddItemDialog() {

        addItemDialog.close();
    }

    public void showViewMenu() {

        viewMenu.toFront();
    }
}
