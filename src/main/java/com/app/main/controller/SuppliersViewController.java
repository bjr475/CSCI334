package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
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
    public BorderPane modMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public TableView tableView;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        editButton.setOnMouseClicked(event -> modMenu.toFront());
        addButton.setOnMouseClicked(event -> modMenu.toFront());

        mainDrawer.setOnDrawerOpening(e -> {
            final Transition transition = mainMenu.getAnimation();
            transition.setRate(1);
            transition.play();
        });
        mainDrawer.setOnDrawerClosing(event -> {
            final Transition transition = mainMenu.getAnimation();
            transition.setRate(-1);
            transition.play();
        });
        mainMenu.setOnMouseClicked(event -> {
            if (mainDrawer.isClosed() || mainDrawer.isClosing()) mainDrawer.open();
            else mainDrawer.close();
        });
    }

    @FXML
    protected void onCancel(ActionEvent event) {
        tableDisplay.toFront();
    }

    /*@FXML
    protected void onSave(ActionEvent event) {
        viewMenu.toFront();
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }*/
}
