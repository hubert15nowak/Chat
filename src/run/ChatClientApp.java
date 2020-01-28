package run;

import chat.ChatClient;
import gui.MainPanel;
import network.Message.*;
import network.server.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp implements MessageReceiver {

    private ChatClient chatClient;
    private Client client;
    private MainPanel mainPanel;

    public ChatClientApp() {

        chatClient = new ChatClient(null);
        createGui();
        connect();
    }

    private void createGui() {
        mainPanel = new MainPanel(this);
        JFrame frame = new JFrame("Chat");
        frame.setMinimumSize(new Dimension(400,300));
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public boolean connect() {
        try {
            Socket socket = new Socket("localhost", 57172);
            client = new Client(socket, this);
            chatClient = new ChatClient(client);
        } catch (IOException e) {
            System.out.println("connection failed");
            return false;
        }
        return true;
    }

    private void close() {
        if(chatClient.getClient() != null) {
            chatClient.getClient().close();
        }
    }

    public void login(String username, String password) {
        ClientLogin clientLogin = new ClientLogin(chatClient.getId(), username, password);
        chatClient.sendMessage(clientLogin);
    }

    public void register(String username, String password) {
        RegisterUser registerUser = new RegisterUser(chatClient.getId(), username, password);
        chatClient.sendMessage(registerUser);
    }

    public void create(String name) {
        ClientCreateRoom clientCreateRoom = new ClientCreateRoom(chatClient.getId() , name);
        chatClient.sendMessage(clientCreateRoom);
    }

    public void enter(String name) {
        ClientEnterRoom clientEnterRoom = new ClientEnterRoom(chatClient.getId(), name);
        chatClient.sendMessage(clientEnterRoom);
    }

    public void leave() {
        ClientLeaveRoom clientLeaveRoom = new ClientLeaveRoom(chatClient.getId());
        chatClient.sendMessage(clientLeaveRoom);
    }

    public void sendMessage(String message) {
        ClientRoomMessage clientRoomMessage = new ClientRoomMessage(chatClient.getId(), message);
        chatClient.sendMessage(clientRoomMessage);
    }



    public void instruction(String s) {
        String []instruction = s.split(" ");
        if(instruction.length > 0) {
            switch (instruction[0]) {
                case "connect":
                    connect();
                    break;

                case "create":
                    if(instruction.length == 2) {
                        ClientCreateRoom clientCreateRoom = new ClientCreateRoom(chatClient.getId() , instruction[1]);
                        chatClient.sendMessage(clientCreateRoom);
                    }
                    else {
                        System.out.println("wrong statement");
                    }
                    break;
                case "enter":
                    if(instruction.length == 2) {
                        ClientEnterRoom clientEnterRoom = new ClientEnterRoom(chatClient.getId(), instruction[1]);
                        chatClient.sendMessage(clientEnterRoom);
                        break;
                    }
                    else {
                        System.out.println("wrong statement");
                    }
                case "leave":
                    if(instruction.length == 1) {
                        ClientLeaveRoom clientLeaveRoom = new ClientLeaveRoom(chatClient.getId());
                        chatClient.sendMessage(clientLeaveRoom);
                        break;
                    }
                    else {
                        System.out.println("wrong statement");
                    }
                case "register":
                    if(instruction.length == 3) {
                        RegisterUser registerUser = new RegisterUser(chatClient.getId(), instruction[1], instruction[2]);
                        chatClient.sendMessage(registerUser);
                        break;
                    }
                case "login":
                    if(instruction.length == 3) {
                        ClientLogin clientLogin = new ClientLogin(chatClient.getId(), instruction[1], instruction[2]);
                        chatClient.sendMessage(clientLogin);
                        break;
                    }
                default:
                    ClientRoomMessage clientRoomMessage = new ClientRoomMessage(chatClient.getId(), s);
                    chatClient.sendMessage(clientRoomMessage);
                    break;
            }
        }
    }

    @Override
    public void receive(Message message) {
        switch(message.getMessageId()) {
            case SERVER_WELCOME_USER:
                WelcomeClient welcomeClient = (WelcomeClient) message;
                chatClient.setId(welcomeClient.getClientId());
                mainPanel.printMessage("Welcome to " + welcomeClient.getName());
                break;
            case SERVER_ACCEPT_USER:
                AcceptClient acceptClient = (AcceptClient) message;
                chatClient.setId(acceptClient.getClientId());
                break;
            case SERVER_CLIENT_LOGGED_IN:
                mainPanel.switchToChat();
                System.out.println("Hello");
                break;
            case SERVER_CLIENT_REGISTRATION_COMPLETED:
                mainPanel.switchToLogin();
                mainPanel.printMessage("Registration completed, please login");
                break;
            case SERVER_ALERT:
                MessageAlert alert = (MessageAlert) message;
                mainPanel.printMessage("Server: " + alert.getAlert());
                break;
            case SERVER_NEW_ROOM_CLIENT:
                ServerNewRoomClient serverNewRoomClient = (ServerNewRoomClient) message;
                mainPanel.newRoomMessage(serverNewRoomClient.getName() + " joined room");
                System.out.println(serverNewRoomClient.getName() + " joined room");
                break;
            case SERVER_ROOM_MESSAGE:
                RoomMessage roomMessage = (RoomMessage) message;
                mainPanel.newRoomMessage(roomMessage.getName() + ": " + roomMessage.getS());
                System.out.println(roomMessage.getName() + ": " + roomMessage.getS());
                break;
            case SERVER_ROOM_LIST:
                ServerRoomsList list = (ServerRoomsList) message;
                mainPanel.roomList(list.getNames());
                break;
        }
    }

    public static void main(String [] args) {
        ChatClientApp chatClientApp = new ChatClientApp();
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        while(!s.toLowerCase().equals("exit")) {
            chatClientApp.instruction(s);
            s = in.nextLine();
        }
        chatClientApp.close();
    }
}
