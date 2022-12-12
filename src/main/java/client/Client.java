package client;

import cinfiguration.FileSystemServerConfigurator;
import cinfiguration.ServerConfiguration;
import cinfiguration.ServerConfigurator;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Scanner;

public class Client implements Runnable{

    private PrintWriter out;
    private BufferedReader in;
    private String nick;

    public Client(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run(){
//        String host = readConfiguration().getHost();
//        int port = readConfiguration().getPort();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите свой никнейм:");
        nick = scanner.nextLine();
        System.out.println("Идёт подключение к чату (для выхода из чата введите \"exit\")");

        while (true) {
            try {
                System.out.println(nick + ": ");
                String message = scanner.nextLine();

                if (message.equals("exit")) {
                    out.println(nick + " - " + Calendar.getInstance().getTime() + ": " + "Пользователь покинул чат");
                    writeClientInLog(in.readLine());
                    break;
                }

                out.println(nick + " - " + Calendar.getInstance().getTime() + ": " + message);
                writeClientInLog(nick + " - " + Calendar.getInstance().getTime() + ": " + message);

                int c = in.read();
                if (c > -1) {
                    System.out.println("reading...");
                    String ms = (char) c + in.readLine();
                    writeClientInLog(ms);
                    System.out.println(ms);
                }
            } catch (IOException e) {
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

    public void getMessage(String message, String nick){
        out.println(nick + " - " + Calendar.getInstance().getTime() + ": " + message);
    }

    public String getNick(){
        return nick;
    }

    private static ServerConfiguration readConfiguration(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        return serverConfigurator
                .loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));
    }
}
