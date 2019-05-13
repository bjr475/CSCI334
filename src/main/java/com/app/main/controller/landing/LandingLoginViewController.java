package com.app.main.controller.landing;

import com.app.main.controller.AChildMainViewController;
import com.app.main.controller.ControllerUtil;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LandingLoginViewController extends AChildMainViewController {
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

    public LandingLoginViewController(ApplicationModel model) {
        super(model);
    }

    /*@FXML
    protected void handleLogin(ActionEvent event) {
    }*/

    @FXML
    public void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);
    }

    @FXML
    protected void handleLogin(ActionEvent event) {
    }
}

