package network.Message;

import java.util.ArrayList;

public class ServerRoomsList extends Message {

    private ArrayList<String> names;

    public ServerRoomsList( ArrayList<String> names) {
        super(MessageId.SERVER_ROOM_LIST);
        this.names = names;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
