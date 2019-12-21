package chat;

import java.util.ArrayList;

public interface ChatListener {
    void welcome(ChatClient client, String name);

    void nameAccepted(ChatClient client);

    void newRoomClient(ChatClient client, ArrayList<ChatClient> clients);

    void sendRoomMessage(ChatClient client, ArrayList<ChatClient> clients, String s);

    void messageAlert(ChatClient client, String alert);
}