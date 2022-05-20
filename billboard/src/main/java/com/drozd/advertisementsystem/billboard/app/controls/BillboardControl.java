package com.drozd.advertisementsystem.billboard.app.controls;

import com.drozd.advertisementsystem.billboard.app.listeners.BillboardListener;
import com.drozd.advertisementsystem.billboard.model.Advertisement;
import com.drozd.advertisementsystem.billboard.rmi.implementation.Billboard;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.Duration;

public class BillboardControl extends Text {
    private final Billboard billboard;
    private Advertisement currentAd;


    public BillboardControl(Billboard billboard) {
        this.billboard = billboard;
        this.setText("No advertisements.");
        this.setFont(Font.font("Forte", 30));
        this.setWrappingWidth(550);
        billboard.addListener(new BillboardListener() {
            @Override
            public void onAdvertisementChange() {
                updateAd();
            }

            @Override
            public void onDurationChange(Duration duration) {

            }
        });
    }

    private void updateAd(){
        this.currentAd = billboard.getCurrentAdvertisement();
        this.setText(currentAd.getText());
    }
}
