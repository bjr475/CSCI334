package com.app.main.controller;

import com.app.main.controller.employee.AChildEmployeeViewController;
import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CustomersViewController extends AChildEmployeeViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public JFXButton catalogue, customer, suppliers, sales, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    public LandingLoginViewController landingloginController;

    @FXML
    public CatalogueViewController catalogController;

    @FXML
    public SuppliersViewController suppliersController;

    @FXML
    public SalesViewController salesController;

    @FXML
    public BorderPane modMenu, viewMenu, searchMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public TableView tableView;

    @FXML
    public Text displayResponse;

    @FXML
    public Pane outsideMenu;

    @FXML
    public JFXRadioButton creditLineT, creditLineF, clubMemberT, clubMemberF;

    public CustomersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        //ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        ToggleGroup creditLine = new ToggleGroup();
        creditLineT.setToggleGroup(creditLine);
        creditLineF.setToggleGroup(creditLine);

        ToggleGroup clubMember = new ToggleGroup();
        clubMemberT.setToggleGroup(clubMember);
        clubMemberF.setToggleGroup(clubMember);

        addButton.setOnMouseClicked(event -> modMenu.toFront());
        tableView.setOnMouseClicked(event -> viewMenu.toFront());
        outsideMenu.setOnMouseClicked(event -> modMenu.toBack());
        searchButton.setOnMouseClicked(event -> searchMenu.toFront());
    }


    @FXML
    protected void onCancel(ActionEvent event) {
        tableDisplay.toFront();
        displayResponse.setText("");
    }

    @FXML
    protected void onSave(ActionEvent event) {
        displayResponse.setText("Catalogue item saved.");
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }

    @FXML
    protected void onClose(ActionEvent event) {
        tableView.toFront();
    }
}
