package com.app.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class.getName());
    private static final SimpleDateFormat databaseDateFormat;
    private static final SimpleDateFormat printFormat;

    static {
        logger.info("Default TimeZone: {}", TimeZone.getDefault());
        databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        databaseDateFormat.setTimeZone(TimeZone.getDefault());

        printFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
        printFormat.setTimeZone(TimeZone.getDefault());
    }

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
    public static String formatPrintDate(Date date) {
        if (date == null) return printFormat.format(Calendar.getInstance().getTime());
        return printFormat.format(date);
    }

    @Nullable
    public static Date parseDatabaseDate(String date) {
        try {
            return databaseDateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("Error loading request timestamp", e);
        }
        return null;
    }
}
