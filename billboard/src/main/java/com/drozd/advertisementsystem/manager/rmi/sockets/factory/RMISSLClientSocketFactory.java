package com.drozd.advertisementsystem.manager.rmi.sockets.factory;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class RMISSLClientSocketFactory implements RMIClientSocketFactory, Serializable {
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return SSLSocketFactory.getDefault().createSocket(host, port);
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
