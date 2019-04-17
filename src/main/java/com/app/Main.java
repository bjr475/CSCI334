package com.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.printf("%s%n", getClass().getResource("main/view/main_frame.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main/view/main_frame.fxml"));
//        loader.setControllerFactory(this::newController);

        Scene primary = new Scene(
                loader.load(),
                1920,
                800
        );
//        primary.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

        primaryStage.setTitle("RASA");
        primaryStage.setScene(primary);

        
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
//        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
