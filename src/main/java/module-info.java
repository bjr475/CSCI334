module ToyShop {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.jfoenix;
//    requires org.jetbrains.annotations;

    // Icon Manager
    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;

    // Icon Fonts
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;

    // Database
    requires java.sql;
    requires sqlite.jdbc;
    requires org.apache.logging.log4j;

    // View Controllers
//    opens com.app.component to javafx.fxml, javafx.controls;
    opens com.app.main.controller to javafx.fxml, javafx.controls;
//    opens com.app.component.controller to javafx.fxml, javafx.controls;
    opens com.app.main.controller.landing to javafx.fxml, javafx.controls;
//    opens com.app.main.controller.manager to javafx.view, javafx.controls;
//    opens com.app.main.controller.customer to javafx.fxml, javafx.controls;
//    opens com.app.component.controller.service to javafx.fxml, javafx.controls;
//    opens com.app.main.controller.professional to javafx.fxml, javafx.controls;
//    opens com.app.main.controller.customer.service to javafx.fxml, javafx.controls;
//    opens com.app.main.controller.landing.register to javafx.fxml, javafx.controls;

    exports com.app;
}