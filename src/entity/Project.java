package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public class Project {
    private static Integer idInc = 1;
    private Integer id;
    private Manager manager;
    private String name;
    private Customer customer;
    private ArrayList<Employee> employees;

    public Project(Manager manager, String name, Customer customer) {
        this.id = idInc++;
        this.manager = manager;
        manager.getProjects().add(this);
        this.name = name;
        this.customer = customer;
        customer.getProjects().add(this);
        this.employees = new ArrayList<Employee>();
    }

    public Project(Integer id, Manager manager, String name, Customer customer) {
        this.id = id;
        this.manager = manager;
        manager.getProjects().add(this);
        this.name = name;
        this.customer = customer;
        customer.getProjects().add(this);
        this.employees = new ArrayList<Employee>();
    }

    public Project(Manager manager, String name, Customer customer, ArrayList<Employee> employees) {
        this.id = idInc++;
        this.manager = manager;
        manager.getProjects().add(this);
        this.name = name;
        this.customer = customer;
        customer.getProjects().add(this);
        this.employees = employees;
        for (Employee employee : employees) {
            employee.getProjects().add(this);
        }
    }

    public Project(Manager manager, String name, Customer customer, Employee... employees) {
        this.id = idInc++;
        this.manager = manager;
        manager.getProjects().add(this);
        this.name = name;
        this.customer = customer;
        customer.getProjects().add(this);
        for (Employee employee : employees) {
            employee.getProjects().add(this);
            this.employees.add(employee);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "" + id + " " + name + " " + manager.getId() + " " + customer.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!getId().equals(project.getId())) return false;
        return getName().equals(project.getName());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
