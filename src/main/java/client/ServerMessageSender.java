package client;

import messagapi.Message;
import messagapi.MessageSender;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerMessageSender implements MessageSender {
    private PrintWriter out;

    public ServerMessageSender(Socket clientSocket){
        try {
           out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message message) {
        out.println(message);
    }
}
