package com.drozd.advertisementsystem.manager.rmi.sockets.factory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.rmi.server.RMIServerSocketFactory;
import java.security.KeyStore;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory {
    private SSLServerSocketFactory serverSocketFactory;

    public RMISSLServerSocketFactory(Path keystorePath, String passphrase) {
        try {
            char[] pass = passphrase.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keystorePath.toFile()), pass);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("PKIX");
            keyManagerFactory.init(keyStore, pass);

            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            serverSocketFactory = sslContext.getServerSocketFactory();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return serverSocketFactory.createServerSocket(port);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
