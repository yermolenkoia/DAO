package dao;

import entity.Manager;
import entity.Project;

import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public interface ManagerDAO {
    Manager findById(Integer id);

    Manager findByName(String name);

    List<Project> getProjects(Manager manager);

    boolean insert(Manager manager);

    boolean delete(Manager manager);
}
