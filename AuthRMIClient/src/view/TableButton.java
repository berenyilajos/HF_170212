package view;

import javax.swing.JButton;

public class TableButton extends JButton {
  
  private int employeeIndex;
  private int buttonIndex;

  public TableButton(int index) {
    super("Change Salary");
    this.employeeIndex = index;
    this.buttonIndex = index;
  }

  public int getEmployeeIndex() {
    return employeeIndex;
  }

  public void setEmployeeIndex(int employeeIndex) {
    this.employeeIndex = employeeIndex;
  }

  public int getButtonIndex() {
    return buttonIndex;
  }

  public void setButtonIndex(int buttonIndex) {
    this.buttonIndex = buttonIndex;
  }
  
}
