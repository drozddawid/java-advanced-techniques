package com.drozd.advertisementsystem.manager.app.listeners;

public interface BillboardEventListener {
    void onNumberOfAdsChange();
    void onDisplayIntervalChange();
    void onRunningChange();
}
