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
    requires org.jetbrains.annotations;

    // View Controllers
    opens com.app.main.controller to javafx.fxml, javafx.controls;
    opens com.app.main.controller.landing to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee.catalogue to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee.search to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee.sales to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee.supplier to javafx.fxml, javafx.controls;
    opens com.app.main.controller.employee.manager to javafx.fxml, javafx.controls;

    exports com.app;
}