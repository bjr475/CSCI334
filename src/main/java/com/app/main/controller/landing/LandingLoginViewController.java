package com.app.main.controller.landing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LandingLoginViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public AnchorPane cataloguePane;

    @FXML
    public AnchorPane landingLoginPane;

    @FXML
    public JFXButton loginButton;

    @FXML
    private Text messageDisplay;

    /*@FXML
    protected void handleLogin(ActionEvent event) {
    }*/

    public void initialize() {

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
}
