package Controller;

import Model.GameButtonsModel;
import View.GameButtonsView;
import interfaces.IServer;
import javafx.application.Platform;

import java.rmi.RemoteException;

public class SenderRecieverForGame implements ISenderRecieverForServer {

    private IServer server;
    private String buttonID;
    private GameButtonsModel gameButtonsModel;
    private String sign;
    private Boolean blockFirstPlayer;

    public SenderRecieverForGame(IServer server, ACTION action, String buttonID,
                                 GameButtonsModel gameButtonsModel, String sign, Boolean blockFirstPlayer){
        this.buttonID = buttonID;
        this.gameButtonsModel = gameButtonsModel;
        this.sign = sign;
        this.blockFirstPlayer = blockFirstPlayer;
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
            server.publishButtons(buttonID, gameButtonsModel.getSign(), gameButtonsModel.getBlockFirstPlayer());
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void writeResults() {
        if(gameButtonsModel.blockTurn(sign,blockFirstPlayer))
            return;

        if(gameButtonsModel.getGameButtons()[Integer.parseInt(buttonID)].getText().equals("")) {
            Platform.runLater(() -> gameButtonsModel.getGameButtons()[Integer.parseInt(buttonID)].setText(sign));
            GameButtonsView setButtonFill = new GameButtonsView(gameButtonsModel.getGameButtons()[Integer.parseInt(buttonID)]);
            setButtonFill.fillButtonText(sign);
            gameButtonsModel.setBlockFirstPlayer(!blockFirstPlayer);
        }



        //sprawdzenie czy już ktoś wygrał
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(gameButtonsModel.judge(sign)) {
                    try {
                        server.endTheGame(sign);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if(gameButtonsModel.Draw() && !gameButtonsModel.judge(sign)) {
                    System.out.println("Draw: " + gameButtonsModel.Draw());
                    try {
                        server.endTheGameWithDraw();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
