package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static model.Model.connect;
import static model.Model.connection;

public class Department extends Model {

  private int id;
  private String name;
  private int managerId;

  public Department(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Department(int id, String name, int managerId) {
    this.id = id;
    this.name = name;
    this.managerId = managerId;
  }

  public static ArrayList<Department> getAll() throws ClassNotFoundException, SQLException {
    connect();
    ArrayList<Department> list = new ArrayList<>();

    String query = "SELECT department_name AS depName, " +
            "department_id AS id, " +
            "manager_id AS managerId " +
            "FROM departments " +
            "ORDER BY depName";

    ResultSet result = connection.createStatement().executeQuery(query);

    while (result.next()) {
      list.add(
        new Department(
          result.getInt("id"),
          result.getString("depName"),
          result.getInt("managerId")
        )
      );
    }

    disconnect();
    return list;
  }
  
  public ArrayList<Employee> getManagers() throws ClassNotFoundException, SQLException {
    connect();
    ArrayList<Employee> list = new ArrayList<>();

    String query = "SELECT " +
            "e1.employee_id AS id, " +
            "e1.first_name AS firstName, " +
            "e1.last_name AS lastName, " +
            "department_name depName, " +
            "e1.salary " +
            "FROM employees e1, departments d " +
            "WHERE d.department_id=e1.department_id " +
            "AND employee_id IN " +
            "(SELECT DISTINCT manager_id FROM employees e2 " +
            "WHERE e2.department_id=?) " +
            "ORDER BY first_name, last_name";

    PreparedStatement ps = connection.prepareStatement(query);
    
    ps.setInt(1, this.id);
    
    ResultSet result = ps.executeQuery();
    
    while (result.next()) {
      list.add(
              new Employee(
                      result.getInt("id"),
                      result.getString("firstName"),
                      result.getString("lastName"),
                      result.getInt("salary"),
                      this.id,
                      result.getString("depName")
              )
      );
    }

    disconnect();
    return list;
  }
    
    public int getId() {
    return id;
  }
    
    public String getName() {
    return name;
  }

  public int getSumSalary() throws SQLException, ClassNotFoundException {
    connect();

    String query = "SELECT SUM(salary) AS sumSalary FROM employees WHERE department_id=" + id;
    ResultSet result = connection.createStatement().executeQuery(query);
    result.next();
    int sumSalary = result.getInt("sumSalary");

    disconnect();

    return sumSalary;
  }
}
