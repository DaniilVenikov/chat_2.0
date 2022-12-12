package connection;

import client.Client;

import java.net.Socket;
import java.sql.Connection;

public interface ClientConnectionListener {

    void connectionCreated(Client c);

    void connectionClosed(Client c);

    void connectionException(Client c, Exception e);

    void sendMessage(String message, String name);
}
