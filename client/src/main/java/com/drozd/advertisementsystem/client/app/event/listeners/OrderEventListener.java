package com.drozd.advertisementsystem.client.app.event.listeners;

public interface OrderEventListener {
    void onOrderPlaced(int orderID);
    void onOrderNotPlaced();
}
