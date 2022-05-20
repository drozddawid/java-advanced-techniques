module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires lib;

    opens com.drozd.app to javafx.fxml;
    exports com.drozd.app;
}