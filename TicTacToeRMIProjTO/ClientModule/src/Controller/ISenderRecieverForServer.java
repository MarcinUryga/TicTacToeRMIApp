package Controller;

import interfaces.IServer;

enum ACTION {Sender, Reciever}

public interface ISenderRecieverForServer {
    void setServer(IServer server);
    void sendData();
    void writeResults();
}
