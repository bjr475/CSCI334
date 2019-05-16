package com.app.main.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ControllerUtil {
    private static final Logger logger = LogManager.getLogger(ControllerUtil.class.getName());

    public static void prepareDrawer(@NotNull JFXDrawer drawer, @NotNull JFXHamburger hamburger) {
        drawer.setDefaultDrawerSize(300);
        drawer.setOnDrawerOpening(e -> {
//            logger.debug("A JFXDrawer is opening: {}", drawer);
            final Transition transition = hamburger.getAnimation();
            transition.setRate(1);
            transition.play();
        });
        drawer.setOnMouseClicked(event -> drawer.close());
        drawer.setOnDrawerClosing(event -> {
//            logger.debug("A JFXDrawer is closing: {}", drawer);
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
