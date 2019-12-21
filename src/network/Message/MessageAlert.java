package network.Message;

public class MessageAlert extends Message {

    private String alert;

    public MessageAlert(String alert) {
        super(MessageId.SERVER_ALERT);
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }
}
