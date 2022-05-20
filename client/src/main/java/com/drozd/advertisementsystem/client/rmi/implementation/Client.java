package com.drozd.advertisementsystem.client.rmi.implementation;

import billboards.IClient;
import billboards.IManager;
import billboards.Order;
import com.drozd.advertisementsystem.client.app.event.listeners.OrderEventListener;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Client extends UnicastRemoteObject implements IClient {
    private List<OrderEventListener> listeners;
    private IManager manager;

    public Client(int port, RMIClientSocketFactory clientSocketFactory, RMIServerSocketFactory serverSocketFactory, IManager manager) throws RemoteException {
        super(port, clientSocketFactory, serverSocketFactory);
        listeners = new ArrayList<>();
        this.manager = manager;
    }


    @Override
    public void setOrderId(int orderId) throws RemoteException {
        if (orderId == -1) {
            fireOrderNotPlacedEvent();
            return;
        } else {
            fireOrderPlacedEvent(orderId);
        }

    }

    private void fireOrderPlacedEvent(int orderID) {
        for (OrderEventListener listener : listeners) {
            listener.onOrderPlaced(orderID);
        }
    }

    private void fireOrderNotPlacedEvent() {
        for (OrderEventListener listener : listeners) {
            listener.onOrderNotPlaced();
        }
    }

    public void addListener(OrderEventListener listener) {
        if (listener != null) listeners.add(listener);
    }
}
