package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import interfaces.AuthInterface;

public class Authentication {
  private AuthInterface auth;

  public Authentication() {
    try {
      Registry r = LocateRegistry.getRegistry("localhost");
      auth = (AuthInterface)r.lookup("authentication");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  
  public boolean login(String user, String pass) {
    try {
      return auth.login(user, pass);
    } catch (RemoteException ex) {
      ex.printStackTrace();
      return false;
    }
  }
  
  public boolean hasPermission(String user, String permission) {
    try {
      return auth.hasPermission(user, permission);
    } catch (RemoteException ex) {
      ex.printStackTrace();
      return false;
    }
  }
}
