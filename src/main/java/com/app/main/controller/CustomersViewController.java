package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CustomersViewController extends AChildMainViewController{
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
    public BorderPane modMenu;

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
        //tableDisplay.toFront();

        ToggleGroup creditLine = new ToggleGroup();
        creditLineT.setToggleGroup(creditLine);
        creditLineF.setToggleGroup(creditLine);

        ToggleGroup clubMember = new ToggleGroup();
        clubMemberT.setToggleGroup(clubMember);
        clubMemberF.setToggleGroup(clubMember);

        addButton.setOnMouseClicked(event -> modMenu.toFront());
        tableView.setOnMouseClicked(event -> modMenu.toFront());
        outsideMenu.setOnMouseClicked(event -> modMenu.toBack());

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
}
