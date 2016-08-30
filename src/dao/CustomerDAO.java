package dao;


import entity.Customer;
import entity.Project;

import java.util.List;

public interface CustomerDAO {
    Customer findById(Integer id);

    Customer findByName(String name);

    List<Project> getProjects(Customer customer);

    boolean insert(Customer customer);

    boolean delete(Customer customer);
}
