package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthInterface extends Remote {
  boolean login(String user, String pass) throws RemoteException;
  boolean hasPermission(String user, String permission) throws RemoteException;
}
