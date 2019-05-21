package com.app.main.controller.landing;

import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;

public class LandingForgotPasswordController extends AChildLandingViewController {
    public LandingForgotPasswordController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {

    }

    public void onLogin() {
        getOwner().onLogin();
    }
}
