package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{
    public void tell (String name)throws RemoteException ;

    public String getName()throws RemoteException ;

    public void tellWhichButton(String name, String sign, Boolean blockFirstPlayer) throws RemoteException;

    public void lockUser(Boolean block) throws RemoteException;

    public void endTheGame(String sign) throws RemoteException;

    public void endTheGameWithDraw() throws RemoteException;


}
