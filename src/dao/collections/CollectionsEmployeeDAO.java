package dao.collections;

import dao.EmployeeDAO;
import entity.Employee;
import entity.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public class CollectionsEmployeeDAO implements EmployeeDAO {
    private ArrayList<Employee> employees;

    public CollectionsEmployeeDAO(ArrayList <Employee> employees){
        this.employees = employees;
    }


    @Override
    public Employee findById(Integer id) {
        for(Employee employee : employees){
            if (employee.getId().equals(id)) return employee;
        }
        return null;
    }

    @Override
    public Employee findByName(String name) {
        for(Employee employee : employees){
            if (employee.getName().equals(name)) return employee;
        }
        return null;
    }

    @Override
    public Employee findByDepartment(String department) {
        for(Employee employee : employees){
            if (employee.getDepartment().equals(department)) return employee;
        }
        return null;
    }

    @Override
    public List<Project> getProjects(Employee employee) {
        return employees.get(employees.indexOf(employee)).getProjects();
    }

    @Override
    public List<Employee> getEmpsWithoutProject() {
        ArrayList<Employee> tmp = new ArrayList<Employee>();
        for (Employee employee : employees){
            if (employee.getProjects().size() == 0){
                tmp.add(employee);
            }
        }
        return tmp;
    }

    @Override
    public boolean insert(Employee employee) {
        return employees.add(employee);
    }

    @Override
    public boolean delete(Employee employee) {
        return employees.remove(employee);
    }

    @Override
    public void addProjects(Employee employee, Project... projects) {
        for(Project project : projects) {
            employees.get(employees.indexOf(employee)).getProjects().add(project);
            project.getEmployees().add(employee);
        }
    }
}
