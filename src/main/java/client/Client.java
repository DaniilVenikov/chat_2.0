package client;

import configuration.FileSystemServerConfigurator;
import configuration.ServerConfiguration;
import configuration.ServerConfigurator;
import messagapi.Message;
import messagapi.MessageDto;
import messagapi.MessageSender;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Scanner;

public class Client{

    public static void main(String[] args) {
        Socket clientSocket = null;
        MessageSender serverMessageSender = null;
        Scanner scanner = new Scanner(System.in);
        String host = readConfiguration().getHost();
        int port = readConfiguration().getPort();
        try {
            clientSocket = new Socket(host, port);
            serverMessageSender = new ServerMessageSender(clientSocket);
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Введите ваш ник");
        String nick = scanner.nextLine();
        System.out.println("Идёт подключение к чату (для выхода из чата введите \"exit\")");

        while (true){
            try {
                assert clientSocket != null;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

                    System.out.println(nick + ": ");
                    String text = scanner.nextLine();

                    Message receivedMessage = new MessageDto();

                    if(text.equals("exit")){
                        receivedMessage.setClientNick(nick);
                        receivedMessage.setMessageTimestamp(new Timestamp(System.currentTimeMillis()));
                        receivedMessage.setMessage("Пользователь покинул чат");
                        serverMessageSender.sendMessage(receivedMessage);
                        writeClientInLog(in.readLine());
                        break;
                    }

                    receivedMessage.setClientNick(nick);
                    receivedMessage.setMessageTimestamp(new Timestamp(System.currentTimeMillis()));
                    receivedMessage.setMessage(text);

                    serverMessageSender.sendMessage(receivedMessage);
                    writeClientInLog(in.readLine());

                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void writeClientInLog(String text){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.log", true))){
            writer.write(text);
            writer.write('\n');
            writer.flush();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static ServerConfiguration readConfiguration(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        return serverConfigurator
                .loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));
    }
}
