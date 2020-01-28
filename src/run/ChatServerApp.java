package run;

import chat.Chat;
import chat.ChatClient;
import chat.ChatListener;
import database.UserDao;
import database.UserDatabaseDao;
import network.ClientReceiver;
import network.Message.*;
import network.Message.ServerClientLoggedIn;
import network.server.Client;
import network.server.Server;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerApp implements ChatListener, MessageReceiver, ClientReceiver {

    private Server server;
    private Chat chat;
    private UserDao userDao;

    public ChatServerApp() throws Exception {
        userDao = new UserDatabaseDao();
        server = new Server(this, this);
        chat = new Chat("Chat Server", this, userDao);
        new Thread(server).start();
    }


    @Override
    public void welcome(ChatClient client, String name) {
        WelcomeClient message = new WelcomeClient(client.getId(), name);
        client.sendMessage(message);
    }

    @Override
    public void registered(ChatClient client) {
        ServerClientRegistrationCompleted message = new ServerClientRegistrationCompleted();
        client.sendMessage(message);
    }

    @Override
    public void logged(ChatClient client) {
        ServerClientLoggedIn message = new ServerClientLoggedIn();
        client.sendMessage(message);
    }

    @Override
    public void newRoomClient(ChatClient client, ArrayList<ChatClient> clients) {
        ServerNewRoomClient m = new ServerNewRoomClient(client.getUsername());
        for (ChatClient c : clients) {
            if (c.getId() != client.getId()) {
                c.sendMessage(m);
            }
        }
    }

    @Override
    public void sendRoomMessage(ChatClient client, ArrayList<ChatClient> clients, String s) {
        RoomMessage m = new RoomMessage(s, client.getUsername());
        for (ChatClient c : clients) {
            if (c.getId() != client.getId()) {
                c.sendMessage(m);
            }
        }
    }

    @Override
    public void sendRoomList(ChatClient client, ArrayList<String> names) {
        ServerRoomsList l = new ServerRoomsList(names);
        client.sendMessage(l);
    }

    @Override
    public void messageAlert(ChatClient client, String alert) {
        MessageAlert m = new MessageAlert(alert);
        client.sendMessage(m);
    }

    @Override
    public void receiveClient(Client client) {
        chat.addNewClient(new ChatClient(client));
    }

    @Override
    public void receive(Message message) {
        switch (message.getMessageId()) {


            case CLIENT_CREATE_ROOM:
                ClientCreateRoom clientCreateRoom = (ClientCreateRoom) message;
                chat.createRoom(clientCreateRoom.getId(), clientCreateRoom.getName());
                break;
            case CLIENT_ENTER_ROOM:
                ClientEnterRoom clientEnterRoom = (ClientEnterRoom) message;
                chat.enterRoom(clientEnterRoom.getId(), clientEnterRoom.getName());
                break;
            case CLIENT_ROOM_MESSAGE:
                ClientRoomMessage clientRoomMessage = (ClientRoomMessage) message;
                chat.roomMessage(clientRoomMessage.getId(), clientRoomMessage.getMessage());
                break;
            case CLIENT_LEAVE_ROOM:
                ClientLeaveRoom clientLeaveRoom = (ClientLeaveRoom) message;
                chat.leaveRoom(clientLeaveRoom.getId());
                break;
            case CLIENT_LOGIN:
                ClientLogin clientLogin = (ClientLogin) message;
                chat.login(clientLogin.getId(), clientLogin.getUsername(), clientLogin.getPassword());
                break;
            case CLIENT_REGISTER:
                RegisterUser registerUser = (RegisterUser) message;
                chat.registerUser(registerUser.getId(), registerUser.getUsername(), registerUser.getPassword());
                break;
        }
    }

    public void close() {
        server.serverOff();
    }

    public static void main(String[] args) {
        try {
            ChatServerApp chatServerApp = new ChatServerApp();
            Scanner in = new Scanner(System.in);
            while (in.nextLine() != "exit") ;
            chatServerApp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
