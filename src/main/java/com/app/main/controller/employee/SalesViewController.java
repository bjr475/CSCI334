package com.app.main.controller.employee;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class SalesViewController extends AChildEmployeeViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public LandingLoginViewController landingloginController;

    @FXML
    public CatalogueViewController catalogController;

    @FXML
    public CustomersViewController customersController;

    @FXML
    public SuppliersViewController suppliersController;

    @FXML
    public JFXButton catalogue, customer, suppliers, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    public BorderPane modMenu, viewMenu, searchMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public TableView tableView;

    public SalesViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        //ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        tableView.toFront();

        /*editButton.setOnMouseClicked(event -> modMenu.toFront());
        addButton.setOnMouseClicked(event -> modMenu.toFront());
        searchButton.setOnMouseClicked(event -> searchMenu.toFront());*/
        tableView.setOnMouseClicked(event -> viewMenu.toFront());
    }

    @FXML
    protected void onCancel(ActionEvent event) {
        tableView.toFront();
    }

    @FXML
    protected void onSave(ActionEvent event) {

    }

    @FXML
    protected void onClose(ActionEvent event) {
        tableView.toFront();
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }
}
