package chat;

import java.util.ArrayList;

public interface ChatListener {
    void welcome(ChatClient client, String name);

    void registered(ChatClient client);

    void logged(ChatClient client);

    void newRoomClient(ChatClient client, ArrayList<ChatClient> clients);

    void sendRoomMessage(ChatClient client, ArrayList<ChatClient> clients, String s);

    void sendRoomList(ChatClient client, ArrayList<String> names);

    void messageAlert(ChatClient client, String alert);
}
