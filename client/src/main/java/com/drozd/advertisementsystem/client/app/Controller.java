package com.drozd.advertisementsystem.client.app;

import billboards.Order;
import com.drozd.advertisementsystem.client.app.controls.OrderControl;
import com.drozd.advertisementsystem.client.app.event.listeners.OrderControlEventListener;
import com.drozd.advertisementsystem.client.app.event.listeners.OrderEventListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.Duration;

public class Controller {
    @FXML
    private TextField adTextTextField;
    @FXML
    private TextField adDurationTextField;
    @FXML
    private Button placeOrderButton;
    @FXML
    private Label infoLabel;
    @FXML
    private VBox placedOrdersVBox;

    @FXML
    protected void initialize() {
        App.client.addListener(new OrderEventListener() {
            @Override
            public void onOrderPlaced(int orderID) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        OrderControl orderControl = new OrderControl(orderID, App.manager, new OrderControlEventListener() {
                            @Override
                            public void onOrderControlRemove(Integer orderID) {
                                OrderControl found = null;
                                for (Node node : placedOrdersVBox.getChildren()) {
                                    if (node instanceof OrderControl) {
                                        OrderControl control = (OrderControl) node;
                                        if (control.getOrderID().equals(orderID)) {
                                            found = control;
                                            break;
                                        }
                                    }
                                }
                                if (found != null) {
                                    placedOrdersVBox.getChildren().remove(found);
                                }
                            }
                        });
                        placedOrdersVBox.getChildren().add(orderControl);
                    }
                });
            }

            @Override
            public void onOrderNotPlaced() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        infoLabel.setText("Order not placed.");
                    }
                });
            }
        });
    }

    @FXML
    protected void placeOrder() {
        if (adTextTextField.getText().length() > 0 && adDurationTextField.getText().length() > 0 && App.client != null) {
            try {
                Order order = new Order();
                order.advertText = adTextTextField.getText();
                order.displayPeriod = Duration.ofSeconds(Integer.parseInt(adDurationTextField.getText()));
                order.client = App.client;

                App.manager.placeOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}