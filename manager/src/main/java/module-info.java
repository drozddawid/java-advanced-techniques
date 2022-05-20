module com.drozd.advertisementsystem.manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    opens com.drozd.advertisementsystem.manager.app to javafx.fxml;
    exports com.drozd.advertisementsystem.manager.app;
    exports com.drozd.advertisementsystem.manager.rmi.implementation;
    exports billboards to java.rmi;
}