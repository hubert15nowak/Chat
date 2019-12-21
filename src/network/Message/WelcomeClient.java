package network.Message;

public class WelcomeClient extends Message {

    private int id;
    private String name;

    public WelcomeClient(int id, String name) {
        super(MessageId.SERVER_WELCOME_USER);
        this.id = id;
        this.name = name;
    }

    public int getClientId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
