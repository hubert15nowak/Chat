package network.server;

import network.ClientReceiver;
import network.Message.MessageReceiver;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private ArrayList<Client> clients;
    public final int PORT = 57172;
    private MessageReceiver receiver;
    private ClientReceiver clientReceiver;
    private boolean running = true;

    public Server(MessageReceiver receiver) {
        this.receiver = receiver;
        clients = new ArrayList<>();
    }

    public Server(MessageReceiver receiver, ClientReceiver clientReceiver) {
        this.receiver = receiver;
        this.clientReceiver = clientReceiver;
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Client client;
            while (running) {
                Socket socket = serverSocket.accept();
                client = new Client(socket, receiver);
                clients.add(client);
                if(clientReceiver != null) {
                    clientReceiver.receiveClient(client);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void serverOff() {
        for(Client client : clients) {
            client.close();
        }
        running = false;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }
}
