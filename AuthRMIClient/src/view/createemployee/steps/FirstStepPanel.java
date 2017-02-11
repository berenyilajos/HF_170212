package view.createemployee.steps;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Employee;

public class FirstStepPanel extends StepPanel {
  
  public FirstStepPanel(String title, Employee employee){
    super(title, employee);
    initComponents();
  }
  
  @Override
  public void initComponents() {
    JPanel pn = new JPanel();
    JLabel instructions = new JLabel("<html><li>New employee's first name and last name without <br>any digit character</li><br>"
            + "<li>Select the department and the job title</li><br>"
            + "<li>Set the employee's salary between the given limits</li></html>");
    pn.add(instructions);

    add(pn);

    instructions.setFont(new Font("Arial",0,16));

  }
  
  @Override
  public boolean checking() {
    return true;
  }
  
}
