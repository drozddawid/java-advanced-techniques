package com.drozd.advertisementsystem.client.app.controls;

import billboards.IManager;
import com.drozd.advertisementsystem.client.app.event.listeners.OrderControlEventListener;
import com.drozd.advertisementsystem.client.rmi.implementation.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.rmi.RemoteException;

public class OrderControl extends HBox {
    private Integer orderID;
    private Label orderIDLabel;
    private Button withdraw;
    private OrderControlEventListener listener;

    public OrderControl(Integer orderID, IManager manager, OrderControlEventListener listener) {
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.orderID = orderID;
        this.listener = listener;
        orderIDLabel = new Label("ID: " + orderID);
        withdraw = new Button("withdraw order");
        withdraw.setOnAction(e -> {
            try {
                manager.withdrawOrder(orderID);
                fireOrderControlRemoveEvent(orderID);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        super.getChildren().addAll(orderIDLabel, withdraw);
    }

    private void fireOrderControlRemoveEvent(Integer orderID){
        listener.onOrderControlRemove(orderID);
    }

    public Integer getOrderID() {
        return orderID;
    }
}
