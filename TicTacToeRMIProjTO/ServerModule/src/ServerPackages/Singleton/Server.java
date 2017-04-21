package ServerPackages.Singleton;

import interfaces.IClient;
import interfaces.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Server  extends UnicastRemoteObject implements IServer {

    private volatile static Server uniqueInstance;

    private Vector v=new Vector();
    private String gameButtonIndex;

    private Server() throws RemoteException{}

    public static Server getInstance() throws RemoteException {
        if(uniqueInstance == null){
            synchronized (Server.class){
                if(uniqueInstance==null){
                    uniqueInstance = new Server();
                }
            }
        }

        return uniqueInstance;
    }

    public boolean login(IClient a) throws RemoteException{
        if(v.size()<2) {
            System.out.println(a.getName() + "  got connected....");

            a.tell("You have Connected successfully.");
            publish(a.getName() + " has just connected.");
            v.add(a);
            return true;
        }
        else{
            a.tell("You have Connected unsuccessfully.\n\"To much users. You have to wait.\"");
            publish("");
            a.lockUser(true);
            return false;
        }
    }

    public boolean logout(IClient a) throws RemoteException{
        if(v.remove(a))
            return true;
        return false;
    }

    public int returnNumberOfUser(){
        return v.size();
    }

    public void endTheGame(String sign){
        for(int i=0;i<v.size();i++){
            try{
                IClient tmp=(IClient) v.get(i);
                tmp.endTheGame(sign);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void endTheGameWithDraw(){
        for(int i=0;i<v.size();i++){
            try{
                IClient tmp=(IClient) v.get(i);
                tmp.endTheGameWithDraw();
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void publish(String s) throws RemoteException{
        System.out.println(s);
        for(int i=0;i<v.size();i++){
            try{
                IClient tmp=(IClient) v.get(i);
                tmp.tell(s);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void publishButtons(String s, String sign, Boolean blockFirstPlayer)throws RemoteException{
        System.out.println("Na serwer dotar� button: " + s);
        gameButtonIndex = s;
        for(int i=0;i<v.size();i++)
        {
            try{
                IClient tmp = (IClient) v.get(i);
                tmp.tellWhichButton(gameButtonIndex, sign, blockFirstPlayer);
            }catch(Exception e){

            }
        }
    }


    public Vector getConnected() throws RemoteException{
        return v;
    }
}
