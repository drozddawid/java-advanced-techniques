package com.drozd.advertisementsystem.manager.rmi.implementation;

import billboards.IBillboard;
import billboards.IManager;
import billboards.Order;
import com.drozd.advertisementsystem.manager.app.listeners.ManagerEventListener;
import com.drozd.advertisementsystem.manager.model.Billboard;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Manager extends UnicastRemoteObject implements IManager {
    private static final AtomicInteger billboardIDCounter = new AtomicInteger(0);
    private static final AtomicInteger orderIDCounter = new AtomicInteger(0);
    private List<Integer /*order ID*/> orders;
    private List<Billboard> billboards;
    private ManagerEventListener eventListener;

    public Manager(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        billboards = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public List<Billboard> getBillboards() {
        return billboards;
    }

    public void setEventListener(ManagerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    private void fireBindBillboardEvent(Billboard billboard) {
        eventListener.onBindBillboard(billboard);
    }

    private void fireUnbindBillboardEvent(Billboard billboard) {
        eventListener.onUnbindBillboard(billboard);
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        int id = Manager.billboardIDCounter.incrementAndGet();
        Billboard b = new Billboard(id, billboard);
        billboards.add(b);
        fireBindBillboardEvent(b);
        return id;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        Integer index = null;
        for (int i = 0; i < billboards.size(); i++) {
            if (billboards.get(i).getId().equals(billboardId)) {
                index = i;
                break;
            }
        }
        if (index != null) {
            fireUnbindBillboardEvent(billboards.get(index));
            billboards.remove(index.intValue());

            return true;
        } else return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        boolean orderAccepted = false;
        int orderID = orderIDCounter.incrementAndGet();
        for (Billboard billboard : billboards) {
            if (billboard.addAdvertisement(order.advertText, order.displayPeriod, orderID))
                orderAccepted = true;

        }
        if (orderAccepted) {
            orders.add(orderID);
            order.client.setOrderId(orderID);
        } else {
            orderIDCounter.decrementAndGet();
        }
        return orderAccepted;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        if (orders.contains(orderId)) {
            boolean wasRemoved = false;
            for (Billboard billboard : billboards) {
                if (billboard.removeAdvertisement(orderId))
                    wasRemoved = true;
            }
            orders.remove(Integer.valueOf(orderId));
            return wasRemoved;
        } else return false;
    }
}
