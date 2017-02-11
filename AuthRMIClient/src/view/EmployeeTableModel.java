package view;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Employee;

public class EmployeeTableModel implements TableModel {
    private ArrayList<Employee> data=null;
    private ArrayList<TableButton> buttons=new ArrayList<TableButton>();

    public EmployeeTableModel(ArrayList<Employee> data, ActionListener al) {
        this.data = data;

        for(int i = 0; i < this.data.size(); i++) {
            TableButton button = new TableButton(i);
            button.addActionListener(al);
            buttons.add(button);
        }
    }

    private String[] columnNames={
            "Department Name", "Employee name", "Salary - $", "Change"
    };

    public void disableButtons() {
      buttons.stream().forEach((bt) -> bt.setEnabled(false));
    }
    
    public void enableButtons() {
      buttons.stream().forEach((bt) -> bt.setEnabled(true));
    }
    
    public Employee getRow(int rowIndex) {
        return data.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
          case 2: return Integer.class;
          case 3: return TableButton.class;
          default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getDepartmentName();
            case 1:
                return data.get(rowIndex).getName();
            case 2:
                return data.get(rowIndex).getSalary();
            case 3:
                return buttons.get(rowIndex);
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      ;
    }
    @Override
    public void addTableModelListener(TableModelListener l) {
        ;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        ;
    }
}
