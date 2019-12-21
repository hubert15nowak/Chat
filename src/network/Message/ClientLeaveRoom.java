package network.Message;

public class ClientLeaveRoom extends ClientMessage {

    public ClientLeaveRoom(int id) {
        super(MessageId.CLIENT_LEAVE_ROOM, id);
    }
}
