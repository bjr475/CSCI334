package com.app.main.controller.landing;

import com.app.main.controller.ControllerUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
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
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);
    }
}

