package network.Message;

import chat.ChatClient;

public class RegisterUser extends ClientMessage {
    private String username;
    private String password;

    public RegisterUser(int id, String username, String password) {
        super(Message.MessageId.CLIENT_REGISTER, id);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
