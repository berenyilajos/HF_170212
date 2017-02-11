
package view.createemployee.steps;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import model.Employee;

public class FourthStepPanel extends StepPanel {
  
  private JTextField tfSalary;
  private JPanel pnCheck;
  private JLabel lbInstructionText=new JLabel();
  
  public FourthStepPanel(String title, Employee employee) {
    super(title, employee);
    
    initComponents();

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        lbInstructionText.setText("Please type the monthly salary from $"+minSalary+" to $"+maxSalary+": ");
      }
    });
  }

  @Override
  public void initComponents() {
    JPanel pn = new JPanel(new BorderLayout());
    JPanel pnSalary=new JPanel();
    pnSalary.setLayout(new FlowLayout(FlowLayout.CENTER));
    pnSalary.add(lbInstructionText);
    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setAllowsInvalid(false);
    formatter.setCommitsOnValidEdit(false);
    tfSalary= new JTextField();
    tfSalary.setColumns(8);
    pnSalary.add(tfSalary);
    pn.add(pnSalary, BorderLayout.NORTH);
    add(pn);
  }

  @Override
  public boolean checking() {
    Integer typedValue=null;
    try {
      typedValue=Integer.parseInt(tfSalary.getText());
      if (typedValue>=minSalary && typedValue<=maxSalary) {
        employee.setSalary(typedValue);
        return true;
      }
      else
        JOptionPane.showMessageDialog(this, "Wrong salary! Please select from this interval: "+minSalary+"-"+maxSalary+".", "Information Message", JOptionPane.INFORMATION_MESSAGE);
    }
    catch (NullPointerException|NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid format!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return false;
  }
  
}
