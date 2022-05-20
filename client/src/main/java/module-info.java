module com.drozd.advertisementsystem.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens com.drozd.advertisementsystem.client.app to javafx.fxml;
    exports com.drozd.advertisementsystem.client.app;
    exports billboards to java.rmi;
}