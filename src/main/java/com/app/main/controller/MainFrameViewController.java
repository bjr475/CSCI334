package com.app.main.controller;

import com.app.database.Database;
import com.app.database.dao.user.User;
import com.app.main.controller.employee.EmployeeViewController;
import com.app.main.controller.landing.LandingViewController;
import com.app.main.model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainFrameViewController extends AViewController {
    private static final Logger logger = LogManager.getLogger(MainFrameViewController.class.getName());

    @FXML
    public LandingViewController landingController;

    @FXML
    public EmployeeViewController employeeController;

    @FXML
    public VBox landingPane;

    @FXML
    public VBox employeePane;


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
        User user = Database.INSTANCE.getUser().login(username, password);
        if (user != null) {
            switch (user.getType()) {
                case ADMIN:
                    getModel().setCurrentUser(Database.INSTANCE.getUser().getAdmin(user.getId()));
                    break;
                case EMPLOYEE:
                    getModel().setCurrentUser(Database.INSTANCE.getUser().getEmployee(user.getId()));
                    break;
            }
            employeeController.gotoCatalogue();
            employeePane.toFront();
            return true;
        }
        return false;
    }
}
