package com.drozd.advertisementsystem.manager.app.controls;

import com.drozd.advertisementsystem.manager.app.listeners.BillboardEventListener;
import com.drozd.advertisementsystem.manager.model.Billboard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.rmi.RemoteException;
import java.time.Duration;

public class BillboardControl extends HBox {
    private final Billboard billboard;
    private final Label id;
    private final Label numberOfAds;
    private final Label displayInterval;
    private final TextField displayIntervalInput;
    private final Label running;
    private final Button changeRunningStateButton;


    public BillboardControl(Billboard billboard) {
        super();
        this.setSpacing(8);
        //this.setMinHeight(35);
        this.setPadding(new Insets(8,20,8,20));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #545454");
        this.billboard = billboard;
        id = new Label("ID: " + billboard.getId().toString());
        id.setMinWidth(50);
        id.setStyle("-fx-background-color: #545454; -fx-text-fill: white");

        numberOfAds = new Label("Number of ads: " + billboard.getNumberOfAds().toString());
        numberOfAds.setMinWidth(100);
        numberOfAds.setStyle("-fx-background-color: #545454; -fx-text-fill: white");

        displayIntervalInput = new TextField();
        displayIntervalInput.setVisible(false);
        displayIntervalInput.setManaged(false);
        displayIntervalInput.setMinWidth(125);
        displayIntervalInput.setTooltip(new Tooltip("Pass the interval length in seconds. (Min 2s, max 600s) Press Enter to apply, ESC to discard changes."));
        displayIntervalInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        Integer input = Integer.parseInt(displayIntervalInput.getCharacters().toString().strip());
                        if(input > 1 && input <= 600){
                            billboard.setDisplayInterval(Duration.ofSeconds(input.longValue()));
                            showEditDisplayIntervalComponent(false);
                        }else displayIntervalInput.setText("");
                    }catch(NumberFormatException | RemoteException e){
                        e.printStackTrace();
                    }
                }else if(event.getCode().equals(KeyCode.ESCAPE)){
                    displayIntervalInput.setText("");
                    showEditDisplayIntervalComponent(false);
                }
            }
        });

        displayInterval = new Label("Display interval (s): " + billboard.getDisplayInterval().getSeconds());
        displayInterval.setMinWidth(125);
        displayInterval.setTooltip(new Tooltip("Double click to modify."));
        displayInterval.setStyle("-fx-background-color: #545454; -fx-text-fill: white");
        displayInterval.setCursor(Cursor.HAND);
        displayInterval.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1){
                    showEditDisplayIntervalComponent(true);
                }
            }
        });

        running = new Label("Running: " + (billboard.isRunning()? "yes" : "no"));
        running.setMinWidth(125);
        running.setStyle("-fx-background-color: #545454; -fx-text-fill: white");

        changeRunningStateButton = new Button("Start");
        changeRunningStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(billboard.isRunning()){
                    try {
                        billboard.stop();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    try{
                        billboard.start();
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        billboard.addListener(new BillboardEventListener() {
            @Override
            public void onNumberOfAdsChange() {
                updateNumberOfAds();
            }

            @Override
            public void onDisplayIntervalChange() {
                updateDisplayInterval();
            }

            @Override
            public void onRunningChange() {
                updateRunning();
            }
        });
        this.getChildren().addAll(id, numberOfAds, displayInterval, displayIntervalInput, running, changeRunningStateButton);
    }

    private void updateNumberOfAds(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                numberOfAds.setText("Number of ads: " + billboard.getNumberOfAds().toString());
            }
        });

    }
    private void updateDisplayInterval(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayInterval.setText("Display interval (s): " + billboard.getDisplayInterval().getSeconds());
            }
        });
    }
    private void updateRunning(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(billboard.isRunning()){
                    running.setText("Running: yes");
                    changeRunningStateButton.setText("Stop");
                }else{
                    running.setText("Running: no");
                    changeRunningStateButton.setText("Start");
                }
            }
        });

    }
    private void showEditDisplayIntervalComponent(boolean show){
        displayInterval.setVisible(!show);
        displayInterval.setManaged(!show);
        displayIntervalInput.setManaged(show);
        displayIntervalInput.setVisible(show);
    }

    public Billboard getBillboard() {
        return billboard;
    }
}
