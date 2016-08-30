package entity;

import java.util.ArrayList;

public class Employee extends Human {
    private static Integer idInc = 1;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String department;

    public Employee(String name, String department, ArrayList<Project> projects) {
        super(idInc, name, projects);
        this.department = department;
        for (Project project : projects) {
            project.getEmployees().add(this);
        }
        ++idInc;
    }

    public Employee(String name, String department) {
        super(idInc, name);
        this.department = department;
        ++idInc;
    }

    public Employee(Integer id, String name, String department) {
        super(id, name);
        this.department = department;
    }

    public Employee(String name, String department, Project... projects) {
        super(idInc, name, projects);
        this.department = department;
        for (Project project : projects) {
            project.getEmployees().add(this);
        }
        ++idInc;
    }

    public String toString() {
        return "" + getId() + " " + getName() + " " + getDepartment();
    }
}
