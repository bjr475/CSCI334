package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SettingsViewController extends AChildEmployeeViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public JFXButton catalogue, customer, suppliers, sales, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    private TextField password, newPassword, repeatNewPassword;

    public SettingsViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        //Util.prepareDrawer(mainDrawer, mainMenu);
    }
}
