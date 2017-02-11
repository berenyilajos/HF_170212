package server.controller;

import interfaces.ModelInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.AuthInterface;
import server.authentication.Authentication;

public class ServerController {
          
  public static void main(String[] args) {
    try {
      Registry r=LocateRegistry.getRegistry();

      Authentication authentication = new Authentication();
      AuthInterface serverAuth=(AuthInterface)UnicastRemoteObject.exportObject(authentication,0);
      r.bind("authentication", serverAuth);

      ModelManager modelManager=new ModelManager();
      ModelInterface serverModel=(ModelInterface)UnicastRemoteObject.exportObject(modelManager,0);
      r.bind("modelinterface", serverModel);
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
  
}
