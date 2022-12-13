package server;

import messagapi.Message;
import messagapi.MessageDto;
import messagapi.MessageSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    MessageSender messageSender;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public ClientConnectionHandler(Socket clientSocket, MessageSender messageSender){
        this.clientSocket = clientSocket;
        this.messageSender = messageSender;
    }


    @Override
    public void run() {

        try{
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }

        while (true){


            Message message = new MessageDto();

            messageSender.sendMessage(message);
        }
    }
}
