
package view.createemployee.steps;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Employee;

public class FifthStepPanel extends StepPanel {
  private JLabel lbInstructionText=new JLabel();
  
  public FifthStepPanel(String title, Employee employee) {
    super(title, employee);
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        lbInstructionText.setText("<html><br>Employee's name: "+employee.getFirstName()+" "+employee.getLastName()+"<br><br>Employees's email: "+
                employee.getEmail()+"<br><br>Employee's phone number: "+employee.getPhoneNumber()+
                "<br><br>Selected department: "+depName+"<br><br>Selected job title: "+ jobTile+
                "<br><br>Selected manager's name: "+managerName+
                "<br><br>Employee's salary: $"+employee.getSalary()+"</html>");
      }
    });
    
    initComponents();
  }

  @Override
  public void initComponents() {
    JPanel pn=new JPanel();
    pn.add((lbInstructionText));
    add(pn);
  }

  @Override
  public boolean checking() {
    return true;
  }

}
