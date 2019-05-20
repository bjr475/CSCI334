package com.app.main.controller.landing;

import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class LandingForgotPasswordController extends AChildLandingViewController {

    @FXML
    public Pane landingLoginPane;

    public LandingForgotPasswordController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {

    }

    public void onLogin() {
        landingLoginPane.toFront();
    }
}
