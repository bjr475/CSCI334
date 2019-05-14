package com.app.main.controller.landing;

import com.app.main.controller.AChildMainViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LandingViewController extends AChildMainViewController {

    @FXML
    public LandingLoginViewController landingLoginController;

    @FXML
    public LandingForgotPasswordController landingForgotController;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public Pane landingLoginPane;

    @FXML
    public Pane landingForgotPasswordPane;

    @FXML
    public JFXButton loginButton;

    @FXML
    public JFXButton forgotButton;


    public LandingViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    private void initialise() {
        landingLoginController.setOwner(this);
        landingForgotController.setOwner(this);

        loginButton.setOnMouseClicked(event -> {
            //if()
            landingLoginPane.toFront();
        });
        forgotButton.setOnMouseClicked(event -> landingForgotPasswordPane.toFront());


    }
}
