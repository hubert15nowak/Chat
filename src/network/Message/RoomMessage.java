package network.Message;

public class RoomMessage extends Message {

    private String s;
    private String name;

    public RoomMessage(String s, String name) {
        super(MessageId.SERVER_ROOM_MESSAGE);
        this.s = s;
        this.name = name;
    }

    public String getS() {
        return s;
    }

    public String getName() {
        return name;
    }
}
