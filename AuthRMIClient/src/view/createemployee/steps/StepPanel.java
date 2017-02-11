package view.createemployee.steps;

import javax.swing.*;
import java.awt.*;
import model.Employee;

public abstract class StepPanel extends JPanel {
  protected String title;
  protected Employee employee;
  protected static Integer minSalary=null, maxSalary=null;
  protected static String depName,jobTile,managerName;
  
  public StepPanel(String title, Employee employee) {
    this.title=title;
    this.employee=employee;
    
    this.setLayout(new BorderLayout());
    
    //TITLE
    JPanel pnTitle=new JPanel();
    JLabel lbTitle=new JLabel(title);
    lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lbTitle.setFont(new Font("Arial", Font.BOLD, 18));
    pnTitle.add(lbTitle);
    this.add(pnTitle, BorderLayout.PAGE_START);
    

  }

  public String getTitle() {
    return title;
  }
  
  public abstract void initComponents ();

  public abstract boolean checking();
  
}
