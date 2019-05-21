package com.app.main.controller.employee;

import com.app.database.Database;
import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsViewController extends AChildEmployeeViewController {
    public TextField first_name;
    public TextField last_name;
    public TextField display_name;
    public TextField email;


    public PasswordField newPassword;
    public PasswordField repeatNewPassword;

    public SettingsViewController(ApplicationModel model) {
        super(model);

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                first_name.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                last_name.textProperty().unbindBidirectional(oldValue.lastNameProperty());
                display_name.textProperty().unbindBidirectional(oldValue.displayNameProperty());
                email.textProperty().unbindBidirectional(oldValue.emailProperty());
            }

            if (newValue != null) {
                first_name.textProperty().bindBidirectional(newValue.firstNameProperty());
                last_name.textProperty().bindBidirectional(newValue.lastNameProperty());
                display_name.textProperty().bindBidirectional(newValue.displayNameProperty());
                email.textProperty().bindBidirectional(newValue.emailProperty());
            }
        });
    }

    @FXML
    public void initialize() {
        //Util.prepareDrawer(mainDrawer, mainMenu);
    }

    public void confirmChanges() {
        Database.INSTANCE.getUser().saveUser(getModel().currentUserProperty().get());
    }

    public void updatePassword() {
        String password = newPassword.getText();
        if (password.equals(repeatNewPassword.getText())) {
            Database.INSTANCE.getUser().updatePassword(
                    getModel().getCurrentUser().getId(),
                    getModel().getCurrentUser().getUserType(),
                    password
            );
        }
    }
}
