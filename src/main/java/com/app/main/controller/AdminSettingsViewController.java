package com.app.main.controller;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminSettingsViewController extends AChildMainViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public JFXButton catalogue, customer, suppliers, sales, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    private TextField password, newPassword, repeatNewPassword;

    public AdminSettingsViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {

    }
}

