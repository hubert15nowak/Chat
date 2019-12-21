package network.Message;

public class ClientCreateRoom extends ClientMessage {

    private String name;

    public ClientCreateRoom(int id, String name) {
        super(MessageId.CLIENT_CREATE_ROOM, id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
