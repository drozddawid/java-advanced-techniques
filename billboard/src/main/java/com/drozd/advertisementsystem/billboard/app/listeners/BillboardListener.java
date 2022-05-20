package com.drozd.advertisementsystem.billboard.app.listeners;

import java.time.Duration;

public interface BillboardListener {
    void onAdvertisementChange();
    void onDurationChange(Duration duration);
}
