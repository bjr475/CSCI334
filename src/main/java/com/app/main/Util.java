package com.app.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class.getName());

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

    public static String formatPrice(Double price) {
        return String.format("$%.02f", price);
    }

    public static String formatPrice(@NotNull DoubleProperty price) {
        return formatPrice(price.get());
    }

    @NotNull
    @Contract(pure = true)
    public static String formatHumanDate(Date dateFirstStocked) {
        return "16/05/2019";
    }
}
