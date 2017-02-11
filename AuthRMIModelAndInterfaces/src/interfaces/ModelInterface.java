package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Department;
import model.Employee;
import model.Job;

public interface ModelInterface extends Remote {
  
  // Employee.getAll()
  ArrayList<Employee> getAllEmployees() throws RemoteException, ClassNotFoundException, SQLException;
  
  // Department.getAll()
  ArrayList<Department> getAllDepartments() throws RemoteException, ClassNotFoundException, SQLException;
  
  // Job.getAll()
  ArrayList<Job> getAllJobs() throws RemoteException, ClassNotFoundException, SQLException;
  
  // department.getManagers()
  ArrayList<Employee> getManagers(Department department) throws RemoteException, ClassNotFoundException, SQLException;
  
  // return {minSalary, maxSalary}
  int[] salaryCalculate(Employee employee) throws RemoteException, ClassNotFoundException, SQLException;
  
  // employee.update()
  boolean update(Employee employee) throws RemoteException, ClassNotFoundException, SQLException;
  
  boolean emailExists(String email) throws RemoteException, ClassNotFoundException, SQLException;
  
  // employee.save()
  int save(Employee employee) throws RemoteException, ClassNotFoundException, SQLException;
  
}
