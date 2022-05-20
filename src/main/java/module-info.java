module catalog {
    requires javafx.controls;
    requires javafx.fxml;
    requires lib;


    opens com.drozd.app to javafx.fxml;
    exports com.drozd.app;
    exports com.drozd.controller;
    opens com.drozd.controller to javafx.fxml;
}