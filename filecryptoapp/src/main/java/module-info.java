module com.drozd.filecryptoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.drozd.filecrypto;

    opens com.drozd.filecryptoapp to javafx.fxml;
    exports com.drozd.filecryptoapp;
}