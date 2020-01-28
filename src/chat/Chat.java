package chat;

import database.UserDao;
import network.server.Client;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Room> rooms;
    private ArrayList<ChatClient> clients;
    private ChatListener chatListener;
    private String name;
    private UserDao userDao;

    public Chat(String name, ChatListener chatListener, UserDao userDao) {
        this.name = name;
        this.chatListener = chatListener;
        rooms = new ArrayList<>();
        clients = new ArrayList<>();
        this.userDao = userDao;
    }

    public void addNewClient(ChatClient client) {
        clients.add(client);
        chatListener.welcome(client, name);
    }

    public void createRoom(int id, String name) {
        ChatClient client = getClient(id);
        if(client != null) {
            switch (client.getStatus()) {
                case LOGGED_IN:
                    boolean newRoom = true;
                    for (Room room : rooms) {
                        if (room.getName().equals(name)) {
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
                    sendRooms();
                    break;
                case IN_ROOM:
                    chatListener.messageAlert(client, "You can't create room while you are in room");
                    break;
                case CONNECTED:
                    chatListener.messageAlert(client, "Enter name");
                    break;
            }
        }
    }

    private void sendRooms() {
        ArrayList<String> names = new ArrayList<>();
        for (Room r : rooms) {
            names.add(r.getName());
        }
        for (ChatClient c: clients) {
            if(c.getStatus() != ChatClient.ClientStatus.CONNECTED) {
                chatListener.sendRoomList(c, names);
            }
        }
    }

    private void moveClientToRoom(Room room, int id) {
        ChatClient client = getClient(id);
        if(client != null) {
            if (client.getStatus() == ChatClient.ClientStatus.LOGGED_IN) {
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
            if (client.getStatus() == ChatClient.ClientStatus.LOGGED_IN) {
                for (Room room : rooms) {
                    if (room.getName().equals(name)) {
                        room.addClient(client);
                        break;
                    }
                }
            } else {
                chatListener.messageAlert(client, "You can't do it,  first choose your name");
            }
        }
    }

    public void registerUser(int clientId, String username, String password) {
        ChatClient client = getClient(clientId);
        if (client != null && client.getStatus() == ChatClient.ClientStatus.CONNECTED) {
            boolean used = clients.stream().anyMatch(chatClient -> username.equals(chatClient.getUsername()));
            if(!used) {
                used = userDao.usernameUsed(username);
            }
            if(!used) {
                userDao.saveUser(username, password);
                chatListener.registered(client);
            }
            else {
                chatListener.messageAlert(client, "This name is already in use");
            }
        }
    }

    public void login(int clientId, String username, String password) {
        ChatClient client = getClient(clientId);
        if (client != null) {
            boolean used = clients.stream().anyMatch(chatClient -> username.equals(chatClient.getUsername()));
            if(!used) {
                used = userDao.checkUserCredentials(username, password);
            } else {
                chatListener.messageAlert(client, "Someone is using this account");
                return;
            }
            if(used) {
                client.setUsername(username);
                client.setStatus(ChatClient.ClientStatus.LOGGED_IN);
                chatListener.logged(client);
                sendRooms();
            }
            else {
                chatListener.messageAlert(client, "Wrong username or password");
            }
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