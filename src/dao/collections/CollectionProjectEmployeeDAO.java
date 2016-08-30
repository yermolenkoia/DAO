package dao.collections;

import dao.ProjectEmployeeDAO;
import entity.Employee;
import entity.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IGOR on 26.02.2016.
 */
public class CollectionProjectEmployeeDAO implements ProjectEmployeeDAO {
    private ArrayList<Project> projects;
    private ArrayList<Employee> employees;

    public CollectionProjectEmployeeDAO(ArrayList<Project> projects, ArrayList<Employee> employees){
        this.projects = projects;
        this.employees = employees;
    }
    @Override
    public List<Integer> findByProjectId(Integer projectId) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (Project project : projects){
            if (project.getId() == projectId){
                for (Employee employee : employees){
                    tmp.add(employee.getId());
                }
            }
            return tmp;
        }
        return null;
    }

    @Override
    public List<Integer> findByEmployeeId(Integer employeeId) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (Employee employee : employees){
            if (employee.getId() == employeeId){
                for (Project project : projects){
                    tmp.add(project.getId());
                }
            }
            return tmp;
        }
        return null;
    }
}
