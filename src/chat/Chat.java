package chat;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Room> rooms;
    private ArrayList<ChatClient> clients;
    private ChatListener chatListener;
    private String name;

    public Chat(String name, ChatListener chatListener) {
        this.name = name;
        this.chatListener = chatListener;
        rooms = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public void addNewClient(ChatClient client) {
        clients.add(client);
        chatListener.welcome(client, name);
    }

    public void createRoom(int id, String name) {
        ChatClient client = getClient(id);
        if(client != null) {
            switch (client.getStatus()) {
                case SET_UP:
                    boolean newRoom = true;
                    for (Room room : rooms) {
                        if (room.getName() == name) {
                            newRoom = false;
                            break;
                        }
                    }
                    if (newRoom) {
                        Room room = new Room(name, client, chatListener);
                        moveClientToRoom(room, client.getId());
                        rooms.add(room);
                    } else {
                        chatListener.messageAlert(client, "Room name used");
                    }
                    break;
                case IN_ROOM:
                    chatListener.messageAlert(client, "You can't crate room while you are in room");
                    break;
                case CONNECTED:
                    chatListener.messageAlert(client, "Enter name");
                    break;
            }
        }
    }

    private void moveClientToRoom(Room room, int id) {
        ChatClient client = getClient(id);
        if(client != null) {
            if (client.getStatus() == ChatClient.ClientStatus.SET_UP) {
                room.addClient(client);
            } else {
                chatListener.messageAlert(client, "You can't do it");
            }
        }
    }

    public void roomMessage(int id, String message) {
        ChatClient client = getClient(id);
        if (client != null) {
            client.getRoom().sendMessage(client, message);
        }
    }

    public void leaveRoom(int id) {
        ChatClient client = getClient(id);
        if(client != null) {
            if (client.getStatus() == ChatClient.ClientStatus.IN_ROOM) {
                client.getRoom().leaveRoom(client);
            } else {
                chatListener.messageAlert(client, "You can't do it");
            }
        }
    }

    public void enterRoom(int id, String name) {
        ChatClient client = getClient(id);
        if(client != null) {
            if (client.getStatus() == ChatClient.ClientStatus.SET_UP) {
                for (Room room : rooms) {
                    if (room.getName().equals(name)) {
                        room.addClient(client);
                        break;
                    }
                }
            } else {
                chatListener.messageAlert(client, "You can't do it");
            }
        }
    }

    public void setClientName(int clientId, String clientName) {
        ChatClient client = getClient(clientId);
        if (client != null) {
            client.setName(clientName);
            client.setStatus(ChatClient.ClientStatus.SET_UP);
            chatListener.nameAccepted(client);
        }
    }

    private ChatClient getClient(int clientId) {
        for (ChatClient client : clients) {
            if (client.getId() == clientId) {
                return client;
            }
        }
        return null;
    }
}