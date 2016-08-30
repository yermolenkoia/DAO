package quires;


import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import dao.ProjectDAO;
import entity.Customer;
import entity.Employee;
import entity.Manager;
import entity.Project;

import java.util.ArrayList;


public class Quires {
    private CustomerDAO customerDAO;
    private ProjectDAO projectDAO;
    private ManagerDAO managerDAO;
    private EmployeeDAO employeeDAO;

    public Quires(EmployeeDAO employeeDAO, ProjectDAO projectDAO, ManagerDAO managerDAO, CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        this.employeeDAO = employeeDAO;
        this.managerDAO = managerDAO;
        this.projectDAO = projectDAO;
    }

    public ArrayList<Employee> getEmployeesWithoutProjects() {
        return (ArrayList<Employee>) employeeDAO.getEmpsWithoutProject();
    }

    public ArrayList<Employee> getEmployeesWithoutProjectsFromDep(String department) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for (Employee employee : getEmployeesWithoutProjects()) {
            if (employee.getDepartment().equals(department)) {
                employees.add(employee);
            }
        }
        return employees;
    }

    public ArrayList<Employee> getEmployeesAtProject(Project project) {
        return project.getEmployees();
    }

    public ArrayList<Project> getEmployeesProject(Employee employee) {
        return employee.getProjects();
    }

    public ArrayList<Project> getProjectsForCustomer(Customer customer) {
        return (ArrayList<Project>) customerDAO.getProjects(customer);
    }

    public ArrayList<Employee> getEmployeesForManager(Manager manager) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        ArrayList<Project> projects = (ArrayList<Project>) managerDAO.getProjects(manager);
        for (Project project : projects) {
            for (Employee employee : projectDAO.getEmployees(project)) {
                if (!(employees.contains(employee))) employees.add(employee);
            }
        }
        return employees;
    }

    public ArrayList<Manager> getManagersForEmployee(Employee employee) {
        ArrayList<Manager> managers = new ArrayList<Manager>();
        for (Project project : employee.getProjects()) {
            if (!managers.contains(project.getManager())) {
                managers.add(project.getManager());
            }
        }
        return managers;
    }

    public ArrayList<Employee> getColleagues(Employee employee) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for (Project project : employee.getProjects()) {
            for (Employee colleague : projectDAO.getEmployees(project)) {
                if ((!colleague.equals(employee)) && (!employees.contains(colleague))) {
                    employees.add(colleague);
                }
            }
        }
        return employees;
    }

    public ArrayList<Employee> getEmployeesForCustomer(Customer customer) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for (Project project : customerDAO.getProjects(customer)) {
            for (Employee employee : projectDAO.getEmployees(project)) {
                if (!(employees.contains(employee))) {
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

}
