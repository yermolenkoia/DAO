package dao.collections;

import dao.ManagerDAO;
import entity.Manager;
import entity.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public class CollectionsManagerDAO implements ManagerDAO {
    private ArrayList<Manager> managers;

    public CollectionsManagerDAO(ArrayList<Manager> managers){
        this.managers = managers;
    }


    @Override
    public Manager findById(Integer id) {
        for(Manager manager : managers){
            if (manager.getId().equals(id)) return manager;
        }
        return null;
    }

    @Override
    public Manager findByName(String name) {
        for(Manager manager : managers){
            if (manager.getName().equals(name)) return manager;
        }
        return null;
    }

    @Override
    public List<Project> getProjects(Manager manager) {
        return managers.get(managers.indexOf(manager)).getProjects();
    }

    @Override
    public boolean insert(Manager manager) {
        return managers.add(manager);
    }

    @Override
    public boolean delete(Manager manager) {
        return managers.remove(manager);
    }
}
