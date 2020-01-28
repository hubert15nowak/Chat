package gui;

import network.Message.ClientCreateRoom;
import network.Message.ClientEnterRoom;
import network.Message.ClientLeaveRoom;
import network.Message.ClientRoomMessage;
import run.ChatClientApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    final static String LOGIN_VIEW= "login view";
    final static String REGISTER_VIEW= "register view";
    final static String CHAT_VIEW= "chat view";
    private LoginView loginView;
    private RegisterView registerView;
    private ChatView chatView;
    private JPanel panel;
    private CardLayout cardLayout;
    private ChatClientApp chatClientApp;

    public MainPanel(ChatClientApp chatClientApp) {
        this.chatClientApp = chatClientApp;
        setLayout(new CardLayout());
        loginView = new LoginView(this);
        registerView = new RegisterView(this);
        chatView = new ChatView(this);
        panel = new JPanel(new CardLayout());
        add(loginView.getPanel(), LOGIN_VIEW);
        add(registerView.getPanel(), REGISTER_VIEW);
        add(chatView.getPanel(), CHAT_VIEW);
        cardLayout = (CardLayout)getLayout();
    }

    public void login(String username, String password) {
        chatClientApp.login(username, password);
    }

    public void register(String username, String password) {
        chatClientApp.register(username, password);
    }

    public void create(String name) {
        chatClientApp.create(name);
    }

    public void enter(String name) {
        chatClientApp.enter(name);
    }

    public void leave() {
        chatClientApp.leave();
    }

    public void sendMessage(String message) {
        chatClientApp.sendMessage(message);
    }

    public void switchToRegister() {
        cardLayout.show(this, REGISTER_VIEW);
    }

    public void switchToChat() {
        cardLayout.show(this, CHAT_VIEW);
    }

    public void switchToLogin() {
        cardLayout.show(this, LOGIN_VIEW);
    }

    public void newRoomMessage(String s) {
        chatView.newRoomMessage(s);
    }

    public void printMessage(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void roomList(ArrayList<String> names) {
        chatView.setRoomsList(names);
    }
}
