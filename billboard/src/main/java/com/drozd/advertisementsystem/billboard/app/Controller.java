package com.drozd.advertisementsystem.billboard.app;

import com.drozd.advertisementsystem.billboard.app.controls.BillboardControl;
import com.drozd.advertisementsystem.billboard.app.listeners.BillboardListener;
import com.drozd.advertisementsystem.billboard.rmi.implementation.Billboard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.Duration;

public class Controller {
    @FXML
    private Label systeminfo;
    @FXML
    private VBox container;

    @FXML
    protected void initialize(){
        container.getChildren().add(new BillboardControl(App.billboard));
        App.billboard.addListener(new BillboardListener() {
            @Override
            public void onAdvertisementChange() {

            }

            @Override
            public void onDurationChange(Duration duration) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        systeminfo.setText("System info: Duration changed to " + duration.getSeconds());
                    }
                });

            }
        });
    }
}