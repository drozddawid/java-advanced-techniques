package com.drozd.advertisementsystem.manager.app;

import billboards.IBillboard;
import billboards.IClient;
import billboards.Order;
import com.drozd.advertisementsystem.manager.app.controls.BillboardControl;
import com.drozd.advertisementsystem.manager.app.listeners.ManagerEventListener;
import com.drozd.advertisementsystem.manager.model.Billboard;
import com.drozd.advertisementsystem.manager.rmi.implementation.Manager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.Objects;

public class Controller {
    @FXML
    private VBox billboardsVBox;

    private Manager manager;

    @FXML
    protected void initialize() {
        manager = App.manager;
        manager.setEventListener(new ManagerEventListener() {
                @Override
                public void onBindBillboard(Billboard billboard) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            billboardsVBox.getChildren().add(new BillboardControl(billboard));
                        }
                    });
                }

                @Override
                public void onUnbindBillboard(Billboard billboard) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ObservableList<Node> children = billboardsVBox.getChildren();
                            Integer index = null;
                            for (int i = 0; i < children.size(); i++) {
                                Node billboardControl = children.get(i);
                                if (billboardControl instanceof BillboardControl) {
                                    BillboardControl control = (BillboardControl) billboardControl;
                                    if (Objects.equals(control.getBillboard().getId(), billboard.getId())) {
                                        index = i;
                                        break;
                                    }
                                }
                            }
                            if(index != null) children.remove(index.intValue());
                        }
                    });
                }
            });
//        IBillboard billboard = mock(IBillboard.class);
//        try {
//            when(billboard.addAdvertisement(anyString(), Duration.ofSeconds(anyLong()), anyInt())).thenReturn(true);
//            when(billboard.getCapacity()).thenReturn(new int[]{0, 1});
//            when(billboard.start()).thenReturn(true);
//            when(billboard.stop()).thenReturn(true);
//
//            manager.bindBillboard(billboard);
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        }
    }

    @FXML
    protected void onClick(){
        Order order = new Order();
        order.advertText = "sometext";
        order.displayPeriod = Duration.ofSeconds(30);
        order.client = new IClient() {
            @Override
            public void setOrderId(int orderId) throws RemoteException {

            }
        };

        try {
            System.out.println(manager.placeOrder(order));
            order.advertText = "second";
            order.displayPeriod = Duration.ofSeconds(60);
            manager.placeOrder(order);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}