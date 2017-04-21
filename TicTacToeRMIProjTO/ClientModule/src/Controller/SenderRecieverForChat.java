package Controller;

import interfaces.IServer;
import javafx.scene.control.TextArea;


public class SenderRecieverForChat implements ISenderRecieverForServer {

    TextArea textArea;
    String chatText;
    IServer server;

    public SenderRecieverForChat(IServer server, ACTION action, TextArea textArea, String chatText){
        this.textArea = textArea;
        this.chatText = chatText;
        setServer(server);
        if(action.equals(ACTION.Sender))
            sendData();
        else if(action.equals(ACTION.Reciever))
            writeResults();
    }

    @Override
    public void setServer(IServer server) {
        this.server = server;
    }

    @Override
    public void sendData() {
        try{
            server.publish(chatText);
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void writeResults() {
        textArea.setText(textArea.getText()+"\n"+chatText);
    }
}
