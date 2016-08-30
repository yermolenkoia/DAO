package dao.collections;

import dao.ProjectDAO;
import entity.Employee;
import entity.Project;

import java.util.ArrayList;
import java.util.List;
public class CollectionsProjectDAO implements ProjectDAO {
    private ArrayList<Project> projects;

    public CollectionsProjectDAO(ArrayList<Project> projects){
        this.projects = projects;
    }

    @Override
    public Project findById(Integer id) {
        for (Project project : projects){
            if(project.getId().equals(id)) return project;
        }
        return null;
    }

    @Override
    public Project findByName(String name) {
        for (Project project : projects){
            if(project.getName().equals(name)) return project;
        }
        return null;
    }

    @Override
    public List<Employee> getEmployees(Project project) {
        return projects.get(projects.indexOf(project)).getEmployees();
    }

    @Override
    public boolean insert(Project project) {
        return projects.add(project);
    }

    @Override
    public boolean delete(Project project) {
        return projects.remove(project);
    }

    @Override
    public void addEmployees(Project project, Employee... employees) {
        for(Employee employee : employees) {
            projects.get(projects.indexOf(project)).getEmployees().add(employee);
            employee.getProjects().add(project);
        }
    }

}
