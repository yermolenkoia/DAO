package dao.collections;

import dao.CustomerDAO;
import entity.Customer;
import entity.Project;

import java.util.ArrayList;
import java.util.List;

public class CollectionsCustomerDAO implements CustomerDAO {

    private ArrayList<Customer> customers;
    public CollectionsCustomerDAO(ArrayList<Customer> customers){
        this.customers = customers;
    }



    @Override
    public Customer findById(Integer id) {
        ArrayList<Customer> tmp = new ArrayList<Customer>();
        for (Customer customer : customers){
            if (customer.getId().equals(id))
                return customer;
        }
        return null;
    }

    @Override
    public Customer findByName(String name) {
        ArrayList<Customer> tmp = new ArrayList<Customer>();
        for (Customer customer : customers){
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    @Override
    public List<Project> getProjects(Customer customer) {
        return customers.get(customers.indexOf(customer)).getProjects();
    }

    @Override
    public boolean insert(Customer customer) {
        return customers.add(customer);
    }

    @Override
    public boolean delete(Customer customer) {
        return customers.remove(customer);
    }
}
