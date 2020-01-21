package run;

import chat.ChatClient;
import network.Message.*;
import network.server.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp implements MessageReceiver {

    private ChatClient chatClient;
    private Client client;

    public ChatClientApp() {
        chatClient = new ChatClient(null);
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
                    //TODO: create registration
                    if(instruction.length == 3) {
                        RegisterUser registerUser = new RegisterUser(chatClient.getId(), instruction[1], instruction[2]);
                        chatClient.sendMessage(registerUser);
                        break;
                    }
                case "login":
                    //TODO: create login
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
                System.out.println("Welcome to " + welcomeClient.getName());
                break;
            case SERVER_ACCEPT_USER:
                AcceptClient acceptClient = (AcceptClient) message;
                chatClient.setId(acceptClient.getClientId());
                break;
            case SERVER_CLIENT_LOGGED_IN:
                System.out.println("Hello");
                break;
            case SERVER_CLIENT_REGISTRATION_COMPLETED:
                System.out.println("Registration completed, please login");
                break;
            case SERVER_ALERT:
                MessageAlert alert = (MessageAlert) message;
                System.out.println("Server: " + alert.getAlert());
                break;
            case SERVER_NEW_ROOM_CLIENT:
                ServerNewRoomClient serverNewRoomClient = (ServerNewRoomClient) message;
                System.out.println(serverNewRoomClient.getName() + " joined room");
                break;
            case SERVER_ROOM_MESSAGE:
                RoomMessage roomMessage = (RoomMessage) message;
                System.out.println(roomMessage.getName() + ": " + roomMessage.getS());
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
