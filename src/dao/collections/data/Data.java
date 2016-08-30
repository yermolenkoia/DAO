package dao.collections.data;

import entity.Customer;
import entity.Employee;
import entity.Manager;
import entity.Project;

import java.util.ArrayList;

/**
 * Created by IGOR on 24.02.2016.
 */
public class Data {
    private ArrayList<Employee> employees;
    private ArrayList<Customer> customers;
    private ArrayList<Project> projects;
    private ArrayList<Manager> managers;

    public Data(){
        employees = new ArrayList<Employee>();
        customers = new ArrayList<Customer>();
        projects = new ArrayList<Project>();
        managers = new ArrayList<Manager>();
    }
    public void init(){
        Customer customer1 = new Customer("Kolos");
        Customer customer2 = new Customer("Rogovoy");
        Manager manager1 = new Manager("Simon");
        Manager manager2 = new Manager("Perevertailo");
        Project project1 = new Project(manager1, "HomeWork", customer1);
        Project project2 = new Project(manager2, "CourseWork", customer1);
        ArrayList<Project> projects1 = new ArrayList<Project>();
        projects1.add(project1);
        projects1.add(project2);
        ArrayList<Project> projects2 = new ArrayList<Project>();
        projects2.add(project1);
        ArrayList<Project> projects3 = new ArrayList<Project>();
        projects3.add(project2);
        Employee employee = new Employee("Kostya", "d1", projects1);
        Employee employee1 = new Employee("Anton", "d2", projects2);
        Employee employee2 = new Employee("Valera", "d2", projects2);
        Employee employee3 = new Employee("Sanya", "d2", projects3);
        Employee employee4 = new Employee("Leha", "d2", projects1);
        Employee employee5 = new Employee("Denis", "d1");
        ArrayList <Employee> employees1 = new ArrayList<Employee>();
        employees1.add(employee2);
        employees1.add(employee1);
        Project project = new Project(manager1, "Lab", customer2, employees1);
        customers.add(customer1);
        customers.add(customer2);
        managers.add(manager1);
        managers.add(manager2);
        projects.add(project1);
        projects.add(project2);
        projects.add(project);
        employees.add(employee);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);

    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
