package network.Message;

public class NewClientMessage extends ClientMessage {


    private String name;

    public NewClientMessage(int id,String name) {
        super(MessageId.CLIENT_NEW_USER,id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
