package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{
    public boolean login (IClient a)throws RemoteException ;

    public boolean logout (IClient a)throws RemoteException ;

    public void publish (String s)throws RemoteException ;

    public void publishButtons (String buttonID, String sign, Boolean blockFirstPlayer) throws RemoteException;

    public void endTheGame(String sign) throws RemoteException;

    public void endTheGameWithDraw() throws RemoteException;

    public int returnNumberOfUser() throws RemoteException;
}
