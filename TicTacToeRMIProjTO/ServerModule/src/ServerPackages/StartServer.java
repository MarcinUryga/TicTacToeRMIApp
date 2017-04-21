package ServerPackages;

import ServerPackages.Singleton.Server;
import interfaces.IServer;

import java.rmi.Naming;

public class StartServer {

    public static void main(String[] args) {
        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(3000);

            IServer b = Server.getInstance();
            Naming.rebind("rmi://localhost/myabc", b);
            System.out.println("[System] Server is ready.");
        }catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }

}