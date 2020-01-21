package chat;

import network.Message.Message;

import java.util.ArrayList;

public class Room {
    private ArrayList<ChatClient> clients;
    private String name;
    private ChatClient owner;
    private ChatListener chatListener;

    public Room(String name, ChatClient owner, ChatListener chatListener) {
        this.name = name;
        this.owner = owner;
        this.chatListener = chatListener;
        clients = new ArrayList<>();
    }

    public void sendToClients(Message message) {
        for(ChatClient client : clients) {
            client.sendMessage(message);
        }
    }

    public void leaveRoom(ChatClient client) {
        clients.remove(client);
        chatListener.messageAlert(client, "You left " + name);
        client.setRoom(null);
        client.setStatus(ChatClient.ClientStatus.LOGGED_IN);
    }

    public int getNumberOfClients() {
        return clients.size();
    }

    public String getName() {
        return name;
    }

    public void addClient(ChatClient client) {
        chatListener.newRoomClient(client, clients);
        chatListener.messageAlert(client, "Entered " + name);
        client.setStatus(ChatClient.ClientStatus.IN_ROOM);
        clients.add(client);
        client.setRoom(this);
    }

    public void sendMessage(ChatClient client, String s) {
        chatListener.sendRoomMessage(client, clients, s);
    }
}
