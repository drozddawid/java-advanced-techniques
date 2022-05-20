package com.drozd.advertisementsystem.manager.app.listeners;

import com.drozd.advertisementsystem.manager.model.Billboard;

public interface ManagerEventListener {
    void onBindBillboard(Billboard billboard);
    void onUnbindBillboard(Billboard billboard);
}
