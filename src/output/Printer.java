package output;

import entity.*;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Map;

/**
 * Created by IGOR on 26.02.2016.
 */
public class Printer {
    public void printProjects(ArrayList<Project> projects, String head){
        System.out.println(head);
        for (Project project :projects){
            System.out.println(project.getId() + " " + project.getName() + " " + project.getCustomer().getId() + " " +
            project.getManager().getId() + " " + project.getEmployees());
        }
    }
    public void printManagerOrCustomer(ArrayList<Human> humans, String head){
        System.out.println(head);
        for (Human human : humans){
            System.out.println(human.getId() + " " + human.getName() + " " + human.getProjects());
        }
    }
    public void printEmployee(ArrayList<Employee> employees, String head){
        System.out.println(head);
        for (Employee employee : employees){
            System.out.println(employee.getId() + " " + employee.getName() + " " + employee.getDepartment() + " "
                    + employee.getProjects());
        }
    }
    public void printProjectEmployee(Map<Integer, Integer> projEmp, String head){
        System.out.println(head);
        for (Map.Entry<Integer, Integer> entry : projEmp.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
