package controller;


import model.*;
import view.View;
import view.TableButton;
import view.EmployeeTableModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import interfaces.ModelInterface;

public class Controller {
    private View view;
    private static EmployeeTableModel etm = null;
    private static ActionListener al;
    private static ModelInterface server;

    public Controller() {
      this.view=new View();
      al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          TableButton bt = (TableButton) e.getSource();
          Employee employee = etm.getRow(bt.getEmployeeIndex());
          view.showDialog(employee, bt.getButtonIndex());
        }
      };
      registry();
      try {
        etm = new EmployeeTableModel(server.getAllEmployees(), al);
        view.setEmployees(etm);
        view.setVisible(true);
      } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Most probably misssing ojdbc driver!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(e.getMessage());
        System.exit(0);
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Querying data failed!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(e.getMessage());
        System.exit(0);
      }
        catch (RemoteException ex) {
        JOptionPane.showMessageDialog(null, "Remote connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex.getMessage());
        System.exit(0);
      }
    }
    
    private void registry() {
    try {
      Registry r = LocateRegistry.getRegistry("localhost");
      server = (ModelInterface) r.lookup("modelinterface");
    } catch (NotBoundException | RemoteException e) {
      e.printStackTrace();
    }
  }

    public static void main(String[] args) {
        new Controller();
    }
    
    public static ActionListener getAl() {
      return al;
    }

    public static EmployeeTableModel getEtm() {
      return etm;
    }

    public static void setEtm(EmployeeTableModel newEtm) {
      etm = newEtm;
    }
    
    public static ModelInterface getServer() {
      return server;
    }
    
}
