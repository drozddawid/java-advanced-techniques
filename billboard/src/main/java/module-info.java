module com.drozd.advertisementsystem.billboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    exports com.drozd.advertisementsystem.billboard.app;
    exports com.drozd.advertisementsystem.billboard.rmi.implementation;
    exports billboards;
    opens com.drozd.advertisementsystem.billboard.app to javafx.fxml;
}