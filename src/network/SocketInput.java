package network;

import network.Message.Message;
import network.Message.MessageReceiver;


import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketInput implements Runnable{
    private Socket socket;
    private MessageReceiver receiver;
    private boolean running;

    public SocketInput(Socket socket, MessageReceiver receiver) {
        this.receiver = receiver;
        this.socket = socket;
        running = true;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            Message message;
            while (running && (message = (Message) in.readUnshared()) != null) {
                receiver.receive(message);
            }
        } catch (Exception e) {

        }
    }

    public void socketOff() {
        running = false;
    }
}
