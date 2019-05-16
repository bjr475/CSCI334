package com.app.main.controller.landing;

import com.app.main.controller.AChildMainViewController;
import com.app.main.controller.ControllerUtil;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class LandingViewController extends AChildMainViewController {
    public JFXDrawer mainDrawer;
    public JFXHamburger mainMenu;

    @FXML
    public LandingLoginViewController landingLoginController;

    @FXML
    public LandingForgotPasswordController landingForgotController;

    @FXML
    public Pane landingLoginPane;

    @FXML
    public Pane landingForgotPasswordPane;


    public LandingViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    private void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);
        landingLoginController.setOwner(this);
        landingForgotController.setOwner(this);

        landingLoginPane.toFront();
    }

    public void onLogin() {
        landingLoginPane.toFront();
    }

    public void onForgot() {
        landingForgotPasswordPane.toFront();
    }

    boolean login(String username, String password) {
        return getOwner().login(username, password);
    }
}
