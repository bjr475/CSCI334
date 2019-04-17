package com.app.main.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.fxml.FXML;

public class CatalogueViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public void initialize() {
        mainDrawer.setOnDrawerOpening(e -> {
            final Transition transition = mainMenu.getAnimation();
            transition.setRate(1);
            transition.play();
        });
        mainDrawer.setOnDrawerClosing(event -> {
            final Transition transition = mainMenu.getAnimation();
            transition.setRate(-1);
            transition.play();
        });
        mainMenu.setOnMouseClicked(event -> {
            if (mainDrawer.isClosed() || mainDrawer.isClosing()) mainDrawer.open();
            else mainDrawer.close();
        });
    }
}
