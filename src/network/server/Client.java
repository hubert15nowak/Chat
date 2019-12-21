package network.server;

import network.Message.Message;
import network.Message.MessageReceiver;
import network.SocketInput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private ObjectOutputStream out;
    private SocketInput input;
    private Socket socket;

    public Client(Socket socket, MessageReceiver receiver) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        input = new SocketInput(socket, receiver);
        new Thread(input).start();
    }

    public boolean sendMessage(Message message) {
        try {
            out.writeUnshared(message);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void close() {
        input.socketOff();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
