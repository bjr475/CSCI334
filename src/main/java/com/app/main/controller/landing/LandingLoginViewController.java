package com.app.main.controller.landing;

import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LandingLoginViewController extends AChildLandingViewController {
    public TextField username;
    public PasswordField password;

    @FXML
    private Text messageDisplay;

    public LandingLoginViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        messageDisplay.setText("");
    }

    public void onForgot() {
        getOwner().onForgot();
    }

    public void onLogin() {
        if (getOwner().login(username.getText(), password.getText())) {
            username.setText("");
            password.setText("");
            messageDisplay.setText("");
        } else {
            messageDisplay.setText("Wrong username or password");
        }
    }
}

