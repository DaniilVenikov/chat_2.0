package messagapi;

import java.sql.Timestamp;
import java.util.UUID;

public class MessageDto implements Message {

    private String clientId;
    private Timestamp timestamp;
    private String message;

    @Override
    public String getClientNick() {
        return clientId;
    }

    @Override
    public Timestamp getMessageTimestamp() {
        return timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setClientNick(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void setMessageTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
