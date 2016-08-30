package dao;

import entity.Employee;
import entity.Project;

import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public interface EmployeeDAO {
    Employee findById(Integer id);

    Employee findByName(String name);

    Employee findByDepartment(String department);

    List<Project> getProjects(Employee employee);

    List<Employee> getEmpsWithoutProject();

    boolean insert(Employee employee);

    boolean delete(Employee employee);

    void addProjects(Employee employee, Project... projects);
}
