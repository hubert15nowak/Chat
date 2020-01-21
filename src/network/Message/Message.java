package network.Message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public enum MessageId {
        SERVER_ACCEPT_USER,
        SERVER_WELCOME_USER,
        SERVER_CLIENT_REGISTRATION_COMPLETED,
        SERVER_ROOM_MESSAGE,
        SERVER_ALERT,
        SERVER_NEW_ROOM_CLIENT,
        SERVER_CLIENT_LOGGED_IN,
        CLIENT_ROOM_MESSAGE,
        CLIENT_LOGIN,
        CLIENT_CREATE_ROOM,
        CLIENT_ENTER_ROOM,
        CLIENT_LEAVE_ROOM,
        CLIENT_REGISTER;
    }

    private MessageId messageId;

    public Message(MessageId messageId) {
        this.messageId = messageId;
    }

    public MessageId getMessageId() {
        return messageId;
    }
}
