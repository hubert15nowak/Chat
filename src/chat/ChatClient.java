package chat;

import network.Message.Message;
import network.server.Client;

public class ChatClient {

    public static int idCounter = 0;



    public enum ClientStatus {
        CONNECTED,
        SET_UP,
        IN_ROOM,
    }
    private Client client;
    private Room room;
    private String name;
    private int id;
    private ClientStatus status;
    public ChatClient(Client client) {
        this.client = client;
        id = newID();
        status = ClientStatus.CONNECTED;
    }

    private int newID() {
        return idCounter++;
    }

    public boolean sendMessage(Message message) {
        return client.sendMessage(message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }
}
