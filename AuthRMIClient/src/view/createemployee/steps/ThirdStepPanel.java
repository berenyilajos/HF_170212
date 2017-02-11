
package view.createemployee.steps;

import controller.Controller;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Department;
import model.Employee;
import model.Job;
import static view.createemployee.steps.StepPanel.minSalary;

public class ThirdStepPanel extends StepPanel {
  private String[] jobsList, depsList, mansList;
  private JComboBox cbJobs, cbDeps, cbManagers;
  private ArrayList<Job> jobList;
  private ArrayList<Department> depList;
  private ArrayList<Employee> manList=null;

  public ThirdStepPanel(String title, Employee employee) {
    super(title, employee);
    initComponents();
    
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        updateCbManagers();
      }
    });
    
    cbDeps.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {       
        updateCbManagers();
      }
    });
  }
  
  private void updateCbManagers() {
    int choosedDep=cbDeps.getSelectedIndex();
    Department choosedDepartment=depList.get(choosedDep);
    try {
      DefaultComboBoxModel cbm=(DefaultComboBoxModel)cbManagers.getModel();
      cbm.removeAllElements();
      
      manList = Controller.getServer().getManagers(choosedDepartment); // new ArrayList<>();
      if (manList.size()==0)
        cbm.addElement("Steven King");
             
      for (Employee manager : manList) {
        cbm.addElement(manager);
      }

    } catch (ClassNotFoundException | SQLException | RemoteException ex) {
      System.out.println("Hiba: "+ex.getMessage());
    }
  }
  
  @Override
  public void initComponents() {
    datas();
    JPanel pn=new JPanel();
    pn.setLayout(new FlowLayout(0));
    JPanel pnDeps= new JPanel();
    pnDeps.add(new JLabel("Choose department:   "));
    cbDeps=new JComboBox(depsList);
    pnDeps.add(cbDeps);
    pn.add(pnDeps);
    JPanel pnJobs= new JPanel();
    pnJobs.add(new JLabel("Choose Job title:          "));
    cbJobs=new JComboBox(jobsList);
    pnJobs.add(cbJobs);
    pn.add(pnJobs);
    JPanel pnManagers= new JPanel();
    pnManagers.add(new JLabel("Choose manager:        "));
    cbManagers=new JComboBox();
    pnManagers.add(cbManagers);
    pn.add(pnManagers);
    add(pn);
  }
  
  void datas(){
    try {
      depList=Controller.getServer().getAllDepartments();
      jobList=Controller.getServer().getAllJobs();
      manList=Controller.getServer().getAllEmployees();
      
      int jobListSize=jobList.size();
      jobsList = new String [jobListSize];
      
      for (int i = 0; i < jobListSize; i++) 
        jobsList[i]=jobList.get(i).getTitle();
     
      int depListSize=depList.size();
      depsList = new String [depListSize];

      for (int i = 0; i < depListSize; i++) {
        depsList[i]= depList.get(i).getName();
      }
      int manListSize=manList.size();
      mansList = new String [manListSize];

      for (int i = 0; i < manListSize; i++) {
        mansList[i]= manList.get(i).getName();
      }

    } catch (ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Most probably misssing ojdbc driver!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex.getMessage());
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Querying data failed!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex.getMessage());
      } catch (RemoteException ex) {
        JOptionPane.showMessageDialog(null, "Remote connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex.getMessage());
      }
    

    }

  @Override
  public boolean checking() {
    int cbDepsSelectedIndex=cbDeps.getSelectedIndex();
    int cbJobsSelectedIndex=cbJobs.getSelectedIndex();
    int cbManagersSelectedIndex=cbManagers.getSelectedIndex();
    
    Department selectedDepartment=depList.get(cbDepsSelectedIndex);
    Job selectedJob=jobList.get(cbJobsSelectedIndex);
    
    if (manList.size()==0) {
      employee.setManagerId(100);
      managerName="Steven King";
    }
    
    else {
      Employee selectedManager =manList.get(cbManagersSelectedIndex);
      employee.setManagerId(selectedManager.getID());
      managerName=manList.get(cbManagersSelectedIndex).getName();
    }
    
    employee.setJobId(selectedJob.getId());
    employee.setDepartmentId(selectedDepartment.getId());

    depName=selectedDepartment.getName();
    jobTile=selectedJob.getTitle();

    
    minSalary=(Integer)selectedJob.getMinSalary();
    maxSalary=(Integer)selectedJob.getMaxSalary();
    return true;
  }
  
}
