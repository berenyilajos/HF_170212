package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Employee extends Model {

  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Date hireDate;
  private String jobId;
  private int managerId;
  private int salary;
  private int departmentId;
  private String departmentName;
  private Department department;

  public static ArrayList<Employee> getAll() throws ClassNotFoundException, SQLException {
    connect();
    ArrayList<Employee> list = new ArrayList<>();

    String query = "SELECT e.employee_id AS id, "
            + "d.department_id AS depId, "
            + "d.department_name AS depName, "
            + "e.first_name AS firstName, "
            + "e.last_name AS lastName, "
            + "e.salary "
            + "FROM departments d "
            + "INNER JOIN employees e "
            + "ON e.department_id=d.department_id "
            + "ORDER BY depName, firstName, lastName";

    ResultSet result = connection.createStatement().executeQuery(query);

    while (result.next()) {
      list.add(
              new Employee(
                      result.getInt("id"),
                      result.getString("firstName"),
                      result.getString("lastName"),
                      result.getInt("salary"),
                      result.getInt("depId"),
                      result.getString("depName")
              )
      );
    }

    disconnect();
    return list;
  }
  
  public Employee() {
    ;
  }

  public Employee(int id, String firstName, String lastName, int salary, 
          int departmentId, String departmentName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.departmentId = departmentId;
    this.departmentName = departmentName;
  }

  public Employee(String firstName, String lastName, String email, 
          String phoneNumber, Date hireDate, String jobId, int managerId, 
          int salary, int departmentId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.hireDate = hireDate;
    this.jobId = jobId;
    this.managerId = managerId;
    this.salary = salary;
    this.departmentId = departmentId;
  }

  public boolean update() throws SQLException, ClassNotFoundException {
    connect();

    String query = "UPDATE employees SET salary=" + this.getSalary() + " WHERE employee_id=" + this.getID();
    int result = connection.createStatement().executeUpdate(query);
    System.out.println(""+result);
    disconnect();

    return result > 0;
  }
  
  private static int getNextId() throws SQLException, ClassNotFoundException {
    String query = "SELECT EMPLOYEES_SEQ.nextval FROM dual";//";
    ResultSet result = connection.createStatement().executeQuery(query);
    result.next();
    int nextId = result.getInt("nextval");
    return nextId;
  }
  
  public int save() throws SQLException, ClassNotFoundException {
    connect();

    int nextId = getNextId();
    
    PreparedStatement ps = connection.prepareStatement("INSERT INTO employees ("
            + "employee_id, "
            + "first_name, "
            + "last_name, "
            + "email, "
            + "phone_number, "
            + "hire_date, "
            + "job_id, "
            + "salary, "
            + "manager_id, "
            + "department_id) VALUES(?,?,?,?,?,?,?,?,?,?)");//");
    
    ps.setInt(1, nextId);
    ps.setString(2, getFirstName());
    ps.setString(3, getLastName());
    ps.setString(4, getEmail());
    ps.setString(5, getPhoneNumber());
    ps.setDate(6, getHireDate());
    ps.setString(7, getJobId());
    ps.setInt(8, getSalary());
    ps.setInt(9, getManagerId());
    ps.setInt(10, getDepartmentId());
    
    ps.executeUpdate();

    disconnect();

    this.id = nextId;
    
    return nextId;
  }

  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public int getID() {
    return id;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public int getSalary() {
    return salary;
  }

  public int getDepartmentId() {
    return departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public Department getDepartment() {
    if (department == null) {
      department = new Department(departmentId, departmentName);
    }
    
    return department;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Date getHireDate() {
    return hireDate;
  }

  public void setHireDate(Date hireDate) {
    this.hireDate = hireDate;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public int getManagerId() {
    return managerId;
  }

  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }

  @Override
  public String toString() {
    //return "Employee{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", hireDate=" + hireDate + ", jobId=" + jobId + ", managerId=" + managerId + ", salary=" + salary + ", departmentId=" + departmentId + ", departmentName=" + departmentName + ", department=" + department + '}';
    return firstName+" "+lastName;
  }
  
  public static boolean emailExists(String email) throws SQLException, ClassNotFoundException {
    connect();
    
    PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as c FROM employees WHERE email=?");
    ps.setString(1, email);
    
    ResultSet result = ps.executeQuery();
    result.next();

    boolean res = result.getInt("c") > 0;
    
    disconnect();
    
    return res;
  }
  
  
}
