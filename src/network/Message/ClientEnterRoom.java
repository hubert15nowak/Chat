package network.Message;

public class ClientEnterRoom extends ClientMessage{

    private String name;

    public ClientEnterRoom(int id, String name) {
        super(MessageId.CLIENT_ENTER_ROOM, id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
