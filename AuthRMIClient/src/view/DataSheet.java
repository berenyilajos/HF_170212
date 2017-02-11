package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Employee;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.text.NumberFormatter;

public class DataSheet extends JDialog implements ActionListener {

  private Employee employee;
  private JFormattedTextField tftNewSalary;
  private JLabel lbName, lbCurrentSalary, lbNewSalary, lbMinMaxSalary;
  private JPanel pnDetails = new JPanel();
  private JPanel pnApprove = new JPanel();
  private JPanel pnSalaryChange = new JPanel();
  private JPanel pnCenter = new JPanel();
  private JPanel pnCenterValues = new JPanel();
  private int typedValue, salaryMin, salaryMax;
  private boolean salaryCheck;
  
  public DataSheet(Frame owner, Employee employee) throws ClassNotFoundException, SQLException, RemoteException {
    super(owner, employee.getName(), true);
    this.employee = employee;
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(400, 200);
    setResizable(false);
    add(pnDetails, BorderLayout.NORTH);
    pnDetails.setLayout(new BorderLayout());
    lbName=new JLabel("<html><br>Employee's name: "+employee.getName()+"<html>");
    lbCurrentSalary=new JLabel("Current salary: $"+employee.getSalary());
    pnDetails.add(lbName,BorderLayout.NORTH);
    pnDetails.add(lbCurrentSalary,BorderLayout.SOUTH);
    lbName.setHorizontalAlignment(SwingConstants.CENTER);
    lbCurrentSalary.setHorizontalAlignment(SwingConstants.CENTER);
    pnSalaryChange.setLayout(new BorderLayout());
    add(pnSalaryChange, BorderLayout.CENTER);
    pnCenter.setLayout(new FlowLayout());
    pnSalaryChange.add(pnCenter, BorderLayout.NORTH);
    lbNewSalary=new JLabel("New salary: ");
    pnCenter.add(lbNewSalary);

    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setAllowsInvalid(false);
    formatter.setCommitsOnValidEdit(false);

    tftNewSalary= new JFormattedTextField(formatter);
    tftNewSalary.addActionListener(this);
    tftNewSalary.setFocusLostBehavior(JFormattedTextField.PERSIST);

    tftNewSalary.setColumns(8);
    pnCenter.add(tftNewSalary);
    salaryCalculate();
    lbMinMaxSalary = new JLabel("Please select a new salary from $"+salaryMin+" to $" +salaryMax);
    pnSalaryChange.add(pnCenterValues, BorderLayout.SOUTH);
    pnCenterValues.add(lbMinMaxSalary);
    
    JButton btSave = new JButton("Save");
    JButton btCancel = new JButton("Cancel");
    add(pnApprove, BorderLayout.SOUTH);
    
    pnApprove.add(btSave, BorderLayout.WEST);
    pnApprove.add(btCancel, BorderLayout.EAST);
    btSave.addActionListener(this);
    btCancel.addActionListener(this);
    setLocationRelativeTo(owner);
  }
  
  void salaryCalculate() throws SQLException, ClassNotFoundException, RemoteException{
    
    int[] salaryMinMax = Controller.getServer().salaryCalculate(employee);
    
    salaryMin = salaryMinMax[0];
    salaryMax = salaryMinMax[1];
            
  }
  
  boolean typedSalaryValueCheck(){
    if (typedValue<salaryMin || typedValue>salaryMax)
      return false;
    return true;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof JButton && e.getActionCommand().equals("Cancel")) {
      dispose();
      return;
    }
    try {
      tftNewSalary.commitEdit();
      typedValue=(Integer)tftNewSalary.getValue();
    }
    catch (NullPointerException | ParseException ex){
      JOptionPane.showMessageDialog(this, "Please type a valid number!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if (typedValue==employee.getSalary()) {
      JOptionPane.showMessageDialog(this, "Same salary typed, please try again!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if (!typedSalaryValueCheck()) {
      JOptionPane.showMessageDialog(this, "Wrong salary! Please select salary from $"+salaryMin+" to $" +salaryMax, "Information Message", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    // all is fine, do change
    employee.setSalary(typedValue);

    dispose();
  }
}
  
  
