package server;

import cinfiguration.FileSystemServerConfigurator;
import cinfiguration.ServerConfiguration;
import cinfiguration.ServerConfigurator;
import client.Client;
import connection.ClientConnectionListener;

import javax.sound.sampled.Line;
import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Server {

    public static final int PORT = readConfiguration().getPort();

    private static List<Client> connections;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true){
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Client client = new Client(clientSocket);
                connectionCreated(client);

                String message = in.readLine();

                if (message != null && message.equals("exit")) {
                    connectionClosed(client);
                    sendMessage("Logged out of chat", client.getNick());
                }

                System.out.println(client.getNick() + "-" + Calendar.getInstance().getTime() + " : " + message);
                sendMessage(message, client.getNick());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void startServer() {
//        System.out.println("Server started");
//        while(true) {
//            try (Socket clientSocket = serverSocket.accept();
//                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//                connectionCreated(clientSocket);
//                new Thread(() ->{
//
//                    String message = "";
//                    try {
//                        message = in.readLine();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                    System.out.println(message);
//
////                        connections.forEach(out.println(message));
//                        for(Socket s : connections){
//                            s.getInputStream(message);
//                        }
//
//                });
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static synchronized void connectionCreated(Client c) {
        connections.add(c);
        System.out.println("Connections was added");
    }


    public static synchronized void connectionClosed(Client c) {
        connections.remove(c);
        System.out.println("Connections was closed");

    }


    public static synchronized void connectionException(Client c, Exception e) {
        connections.remove(c);
        System.out.println("Connections was closed");
        e.printStackTrace();
    }

    private static ServerConfiguration readConfiguration(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        return serverConfigurator
                .loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));
    }


    public static synchronized void sendMessage(String message, String name) {
        for(Client c : connections){
            if(!c.getNick().equals(name)){
                c.getMessage(message, name);
            }
        }
    }
}
