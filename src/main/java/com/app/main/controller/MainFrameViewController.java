package com.app.main.controller;

import com.app.main.controller.employee.EmployeeViewController;
import com.app.main.controller.landing.LandingViewController;
import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainFrameViewController extends AViewController {
    private static final Logger logger = LogManager.getLogger(MainFrameViewController.class.getName());

    @FXML
    public LandingViewController landingController;

    @FXML
    public EmployeeViewController employeeController;

    @FXML
    public Pane landingPane;

    @FXML
    public Pane employeePane;


    public MainFrameViewController(ApplicationModel model) {
        super(model);
    }


    @FXML
    private void initialize() {
        landingController.setOwner(this);
        employeeController.setOwner(this);

        landingPane.toFront();
    }

    public void logout() {
        landingPane.toFront();
    }

    public boolean login(String username, String password) {
        logger.info("Attempting to login with username: {} and password: {}", username, password);
        employeePane.toFront();
        return true;
    }
}
