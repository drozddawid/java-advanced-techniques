package com.drozd.advertisementsystem.billboard.app;

import billboards.IManager;
import com.drozd.advertisementsystem.billboard.rmi.implementation.Billboard;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLClientSocketFactory;
import com.drozd.advertisementsystem.manager.rmi.sockets.factory.RMISSLServerSocketFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

public class App extends Application {
    public static IManager manager;
    public static Billboard billboard;
    private static Integer billboardID;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 200);
        stage.setTitle("Billboard " + billboardID);
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                manager.unbindBillboard(billboardID);
                UnicastRemoteObject.unexportObject(billboard, true);
                billboard.shutdownTasks();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }

    /**
     * @param args args[0] - host
     *             args[1] - port
     *             args[2] - manager registry name
     *             args[3] - billboard port (must be unbinded)
     *             args[4] - keystore path
     *             args[5] - keystore password
     */
    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            if (args.length == 6) {
                RMIClientSocketFactory clientSocketFactory = new RMISSLClientSocketFactory();
                RMIServerSocketFactory serverSocketFactory = new RMISSLServerSocketFactory(Paths.get(args[4]), args[5]);
                Integer registryPort = Integer.parseInt(args[1]);
                Integer billboardPort = Integer.parseInt(args[3]);
                Registry registry = LocateRegistry.getRegistry(args[0], registryPort, clientSocketFactory);
                manager = (IManager) registry.lookup(args[2]);
                billboard = new Billboard(billboardPort, clientSocketFactory, serverSocketFactory, 10, Duration.ofSeconds(5));
                billboardID = manager.bindBillboard(billboard);
                launch();
            } else {
                System.out.println(
                        "args[0] - host" +
                                "\nargs[1] - port" +
                                "\nargs[2] - manager registry name" +
                                "\nargs[3] - billboard port (must be unbinded)" +
                                "\nargs[4] - keystore path" +
                                "\nargs[5] - keystore password");
                System.exit(1);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }


    }
}