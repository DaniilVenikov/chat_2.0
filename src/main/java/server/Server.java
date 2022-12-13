package server;

import configuration.FileSystemServerConfigurator;
import configuration.ServerConfiguration;
import configuration.ServerConfigurator;
import messagapi.MessageSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.List;

public class Server {

    public static final int PORT = readConfiguration().getPort();

    private static List<ClientConnectionHandler> connections;

    public static void main(String[] args) {
        MessageSender messageSender = new AllClientMessageSender(connections);
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true){
                Socket clientSocket = serverSocket.accept();
                ClientConnectionHandler client = new ClientConnectionHandler(clientSocket, messageSender);
                connections.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ServerConfiguration readConfiguration(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        return serverConfigurator
                .loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));
    }
}
