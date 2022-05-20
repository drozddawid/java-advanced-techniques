module com.drozd.processloader {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.drozd.processloader to javafx.fxml;
    exports com.drozd.processloader;
    exports processing;
}