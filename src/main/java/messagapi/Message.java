package messagapi;

import java.sql.Timestamp;
import java.util.UUID;

public interface Message {

    String getClientNick();

    Timestamp getMessageTimestamp();

    String getMessage();

    void setClientNick(String nick);

    void setMessageTimestamp(Timestamp timestamp);

    void setMessage(String text);
}
