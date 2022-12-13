package server;

import messagapi.Message;
import messagapi.MessageSender;
import java.util.List;

public class AllClientMessageSender implements MessageSender {

    private List<ClientConnectionHandler> connections;

    public AllClientMessageSender(List<ClientConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public void sendMessage(Message message) {
        for (ClientConnectionHandler connection : connections) {
            sendMessage(connection, message);
        }
    }

    private void sendMessage(ClientConnectionHandler connectionHandler, Message message) {
        //TODO: реализовать отправку сообщения
    }
}
