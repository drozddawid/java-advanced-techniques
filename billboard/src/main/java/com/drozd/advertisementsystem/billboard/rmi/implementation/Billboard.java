package com.drozd.advertisementsystem.billboard.rmi.implementation;

import billboards.IBillboard;
import com.drozd.advertisementsystem.billboard.app.listeners.BillboardListener;
import com.drozd.advertisementsystem.billboard.model.Advertisement;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLClientSocketFactory;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLServerSocketFactory;

import java.io.Serializable;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Billboard extends UnicastRemoteObject implements IBillboard{
    private final Object mutex = new Object();
    private List<Advertisement> advertisements;
    private Advertisement currentAdvertisement;
    private Advertisement defaultAdvertisement;
    private Integer advertisementsMaxCapacity;
    private Duration displayInterval;
    private List<BillboardListener> listeners;
    private ScheduledExecutorService changeAdExecutor;
    private ScheduledFuture changeAdExecutorFuture;
    private Long lastTimeAdChanged = null;

    public Billboard(int port, RMIClientSocketFactory clientSocketFactory, RMIServerSocketFactory serverSocketFactory, Integer advertisementsMaxCapacity, Duration displayInterval) throws RemoteException {
        super(port, clientSocketFactory, serverSocketFactory);
        this.advertisementsMaxCapacity = advertisementsMaxCapacity;
        this.displayInterval = displayInterval;
        advertisements = new ArrayList<>(advertisementsMaxCapacity);
        defaultAdvertisement = new Advertisement(-1, "No advertisements.", Duration.ofDays(1000));
        advertisements.add(defaultAdvertisement);
        currentAdvertisement = defaultAdvertisement;
        listeners = new ArrayList<>();
        changeAdExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void setNextAdvertisement() {
        try {
            synchronized (mutex) {
                if (advertisements.size() > 0) {
                    int currentIndex = advertisements.indexOf(currentAdvertisement);
                    if (lastTimeAdChanged != null) { // ad with order id -1 is default ad, and it shouldn't be removed
                        if (currentAdvertisement.getOrderID() != -1 && !currentAdvertisement.reduceDuration(Duration.ofMillis(System.currentTimeMillis() - lastTimeAdChanged))) {
                            advertisements.remove(currentAdvertisement);
                        }
                    }
                    currentIndex = advertisements.size() > 0 ? ( (currentIndex + 1) >= advertisements.size() ? 1 : currentIndex + 1) : 0;
                    currentAdvertisement = advertisements.get(currentIndex);
                    lastTimeAdChanged = System.currentTimeMillis();
                    fireAdvertisementChangeEvent();
                } else {
                    currentAdvertisement = defaultAdvertisement;
                    fireAdvertisementChangeEvent();
                }
            }
        } finally {
            changeAdExecutorFuture = changeAdExecutor.schedule(this::setNextAdvertisement, displayInterval.getSeconds(), TimeUnit.SECONDS);
        }
    }

    public Advertisement getCurrentAdvertisement() {
        return currentAdvertisement;
    }

    public void addListener(BillboardListener listener) {
        if (listener != null) listeners.add(listener);
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        if (advertText != null && displayPeriod != null && !displayPeriod.isNegative() && !displayPeriod.isZero() && advertisements.size() < advertisementsMaxCapacity) {
            Advertisement ad = new Advertisement(orderId, advertText, displayPeriod);
            synchronized (mutex) {
                advertisements.add(ad);
            }
            return true;
        } else return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        synchronized (mutex) {
            Advertisement found = null;
            for (Advertisement advertisement : advertisements) {
                if (advertisement.getOrderID().equals(orderId)) found = advertisement;
            }
            if (found != null) {
                if (currentAdvertisement == found) currentAdvertisement = defaultAdvertisement;
                advertisements.remove(found);
                fireAdvertisementChangeEvent();
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[]{advertisementsMaxCapacity, advertisementsMaxCapacity - advertisements.size()};
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        if (displayInterval != null && displayInterval.compareTo(Duration.ofSeconds(1)) > 0) {
            this.displayInterval = displayInterval;
            fireDurationChangeEvent(displayInterval);
        }
    }

    @Override
    public boolean start() throws RemoteException {
        if (changeAdExecutorFuture == null || changeAdExecutorFuture.isCancelled()) {
            changeAdExecutorFuture = changeAdExecutor.schedule(this::setNextAdvertisement, 0, TimeUnit.SECONDS);
            return true;
        } else return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        if (changeAdExecutorFuture != null && !changeAdExecutorFuture.isCancelled()) {
            changeAdExecutorFuture.cancel(true);
            currentAdvertisement = defaultAdvertisement;
            fireAdvertisementChangeEvent();
            return true;
        }
        return false;
    }

    private void fireDurationChangeEvent(Duration duration) {
        for (BillboardListener listener : listeners) listener.onDurationChange(duration);
    }

    private void fireAdvertisementChangeEvent() {
        for (BillboardListener listener : listeners) listener.onAdvertisementChange();
    }

    public void shutdownTasks(){
        changeAdExecutor.shutdownNow();
        try {
            changeAdExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
