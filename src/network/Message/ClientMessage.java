package network.Message;

public abstract class ClientMessage extends Message{

    private int id;

    public ClientMessage(MessageId messageId, int id) {
        super(messageId);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
