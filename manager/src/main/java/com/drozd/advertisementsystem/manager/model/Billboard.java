package com.drozd.advertisementsystem.manager.model;

import billboards.IBillboard;
import com.drozd.advertisementsystem.manager.app.listeners.BillboardEventListener;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Billboard{
    private final Integer id;
    private final IBillboard billboard;
    private Integer numberOfAds;
    private Duration displayInterval;
    private boolean running;
    private List<BillboardEventListener> eventListeners;

    public Billboard(Integer id, IBillboard billboard) {
        this.id = id;
        this.billboard = billboard;
        numberOfAds = 0;
        displayInterval = Duration.ofSeconds(10);
        running = false;
        eventListeners = new ArrayList<>();
    }

    public void addListener(BillboardEventListener listener){
        if(listener != null) eventListeners.add(listener);
    }

    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        if(billboard.addAdvertisement(advertText,displayPeriod,orderId)){
            numberOfAds++;
            fireNumberOfAdsChangeEvent();
            return true;
        }else return false;
    }

    public boolean removeAdvertisement(int orderId) throws RemoteException {
        if(billboard.removeAdvertisement(orderId)){
            numberOfAds--;
            fireNumberOfAdsChangeEvent();
            return true;
        }else return false;
    }

    public int[] getCapacity() throws RemoteException {
        return billboard.getCapacity();
    }

    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        this.displayInterval = displayInterval;
        billboard.setDisplayInterval(displayInterval);
        fireDisplayIntervalChangeEvent();
    }

    public boolean start() throws RemoteException {
        if(billboard.start()){
            running = true;
            fireRunningChangeEvent();
            return true;
        }return false;
    }

    public boolean stop() throws RemoteException {
        if(billboard.stop()){
            running = false;
            fireRunningChangeEvent();
            return true;
        }return false;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumberOfAds() {
        return numberOfAds;
    }

    public Duration getDisplayInterval() {
        return displayInterval;
    }

    public boolean isRunning() {
        return running;
    }

    private void fireNumberOfAdsChangeEvent(){
        for(BillboardEventListener l : eventListeners){
            l.onNumberOfAdsChange();
        }
    }

    private void fireDisplayIntervalChangeEvent(){
        for(BillboardEventListener l : eventListeners){
            l.onDisplayIntervalChange();
        }
    }

    private void fireRunningChangeEvent(){
        for(BillboardEventListener l : eventListeners){
            l.onRunningChange();
        }
    }
}
