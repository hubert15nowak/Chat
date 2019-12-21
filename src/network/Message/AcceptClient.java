package network.Message;

public class AcceptClient extends Message {


    private final int clientId;

    public AcceptClient(int clientId) {
        super(MessageId.SERVER_ACCEPT_USER);
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
