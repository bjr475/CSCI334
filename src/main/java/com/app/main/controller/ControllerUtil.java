package com.app.main.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ControllerUtil {
    public static final Logger logger = LogManager.getLogger("ViewController");

    public static void prepareDrawer(JFXDrawer drawer, JFXHamburger hamburger) {
        drawer.setOnDrawerOpening(e -> {
            final Transition transition = hamburger.getAnimation();
            transition.setRate(1);
            transition.play();
        });
        drawer.setOnDrawerClosing(event -> {
            final Transition transition = hamburger.getAnimation();
            transition.setRate(-1);
            transition.play();
        });
        hamburger.setOnMouseClicked(event -> {
            if (drawer.isClosed() || drawer.isClosing()) drawer.open();
            else drawer.close();
        });
    }
}
