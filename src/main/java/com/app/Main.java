package com.app;

import com.app.database.Database;
import com.app.main.model.ApplicationModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    private static final Logger logger = LogManager.getLogger("Main");

    static {
        final Logger logger = LogManager.getLogger("System");

        System.setErr(new PrintStream(System.err) {
            @Override
            public void print(String s) {
                logger.error(s);
            }

            @Override
            public void println(String x) {
                print(x);
            }
        });

        System.setOut(new PrintStream(System.out) {
            @Override
            public void print(String s) {
                logger.info(s);
            }

            @Override
            public void println(String x) {
                print(x);
            }
        });
    }

    private ApplicationModel model;

    public static void main(String[] args) {
        launch(args);
    }

    @NotNull
    private Object newController(@NotNull Class<?> type) {
        try {
            return type.getDeclaredConstructor(ApplicationModel.class).newInstance(model);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Failed to create New View Controller ({}) with associated model ({})", type, model);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        logger.debug("{}", getClass().getResource("main/view/main_frame.fxml"));

        try (Connection connection = Database.INSTANCE.openConnection()) {
            logger.info("Successful connection to database: {}", connection);
        } catch (SQLException e) {
            logger.error("Failed to open database", e);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main/view/main_frame.fxml"));
        model = new ApplicationModel();
        loader.setControllerFactory(this::newController);
        Scene primary = new Scene(loader.load(), 1440, 768);
        Font.loadFont(getClass().getResourceAsStream("/com/app/fonts/Roboto-Light.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/app/fonts/Roboto-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/app/fonts/Roboto-Regular.ttf"), 16);
        primary.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setTitle("Tim's Hobby Shop");
        primaryStage.setScene(primary);
        primaryStage.setWidth(1440);
        primaryStage.setHeight(768);
        primaryStage.show();
    }
}
