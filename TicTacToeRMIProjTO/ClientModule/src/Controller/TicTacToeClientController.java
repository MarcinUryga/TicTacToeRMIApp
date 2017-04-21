package Controller;

import Model.GameButtonsModel;
import interfaces.IClient;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static Controller.ACTION.*;


public class TicTacToeClientController extends UnicastRemoteObject implements IClient {

    private String name;
    private Controller con;
    private GameButtonsModel gameButtonsModel;
    private ISenderRecieverForServer p2pController;

    public TicTacToeClientController(String n, ISenderRecieverForServer p2pController) throws RemoteException {
        name=n;
        this.p2pController = p2pController;
    }

    public void tell(String st) throws RemoteException{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                p2pController = new SenderRecieverForChat(con.getServer(), Reciever, con.getTextArea(), st);
            }
        });

    }

    public void endTheGame(String sign){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                con.displayAlert("Game Over", gameButtonsModel.setFinalText(sign));
                gameButtonsModel.resetButtons();
            }
        });
    }

    public void endTheGameWithDraw(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                con.displayAlert("Draw", "It's a Draw!");
                gameButtonsModel.resetButtons();
            }
        });
    }

    public void tellWhichButton(String index, String sign, Boolean blockFirstPlayer) throws RemoteException{
        p2pController = new SenderRecieverForGame(con.getServer(),
                Reciever, index, gameButtonsModel, sign, blockFirstPlayer);
    }

    public String getName() throws RemoteException{
        return name;
    }

    public void lockUser(Boolean block) throws RemoteException{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameButtonsModel.setBlockAccess(block);
            }
        });

    }


    public void setGameButtonsModel(GameButtonsModel g){
        gameButtonsModel = g;
    }

    public void setController(Controller t){
        con=t ;
    }
}
