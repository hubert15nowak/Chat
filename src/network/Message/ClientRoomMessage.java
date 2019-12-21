package network.Message;

public class ClientRoomMessage extends ClientMessage {

    private String message;

    public ClientRoomMessage(int id,String message) {
        super(MessageId.CLIENT_ROOM_MESSAGE, id);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
