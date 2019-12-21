package network;

import network.server.Client;

public interface ClientReceiver {
    void receiveClient(Client client);
}
