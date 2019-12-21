package network.Message;

public class ServerNewRoomClient extends Message{

    private String name;

    public ServerNewRoomClient(String name) {
        super(MessageId.SERVER_NEW_ROOM_CLIENT);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
