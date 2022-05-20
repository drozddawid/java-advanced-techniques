package com.drozd.advertisementsystem.manager.app;

import com.drozd.advertisementsystem.manager.rmi.implementation.Manager;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLClientSocketFactory;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLServerSocketFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class App extends Application {
    public static Manager manager;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Manager");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
                    try {
                        UnicastRemoteObject.unexportObject(manager, true);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
        );
        stage.show();
    }

    /**
     * @param args args[0] port
     *             args[1] keystore path
     *             args[2] keystore pass
     */
    public static void main(String[] args) {
        try {
            if (args.length == 3) {
                if (System.getSecurityManager() == null) {
                    System.setSecurityManager(new SecurityManager());
                }
                RMIServerSocketFactory serverSocketFactory = new RMISSLServerSocketFactory(Paths.get(args[1]), args[2]);
                RMIClientSocketFactory clientSocketFactory = new RMISSLClientSocketFactory();

                manager = new Manager(Integer.parseInt(args[0]), clientSocketFactory, serverSocketFactory);
                Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]), clientSocketFactory, serverSocketFactory);
                registry.bind("Manager", manager);
                launch();
            } else {
                System.out.println("args[0] port\n" +
                        "args[1] keystore path\n" +
                        "args[2] keystore pass");
                System.exit(1);
            }
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}