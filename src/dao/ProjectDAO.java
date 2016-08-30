package dao;


import entity.Employee;
import entity.Project;

import java.util.List;

public interface ProjectDAO {
    Project findById(Integer id);

    Project findByName(String name);

    List<Employee> getEmployees(Project project);

    boolean insert(Project project);

    boolean delete(Project project);

    void addEmployees(Project project, Employee... employees);
}
