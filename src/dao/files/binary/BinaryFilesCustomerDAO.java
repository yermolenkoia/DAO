package dao.files.binary;

import dao.CustomerDAO;
import entity.Customer;
import entity.Project;

import java.util.List;

/**
 * Created by IGOR on 18.02.2016.
 */
public class BinaryFilesCustomerDAO implements CustomerDAO {
    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public Customer findByName(String name) {
        return null;
    }

    @Override
    public List<Project> getProjects(Customer customer) {
        return null;
    }

    @Override
    public boolean insert(Customer customer) {
        return false;
    }

    @Override
    public boolean delete(Customer customer) {
        return false;
    }
}
