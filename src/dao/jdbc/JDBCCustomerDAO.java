package dao.jdbc;

import dao.CustomerDAO;
import entity.Customer;
import entity.Project;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class JDBCCustomerDAO extends JDBCConstants implements CustomerDAO {


    @Override
    public Customer findById(Integer id) {
        connectToDB();
        String sql = "SELECT id, name FROM customers " +
                "WHERE id = " + id;
        return getCst(sql);
    }

    @Override
    public Customer findByName(String name) {
        connectToDB();
        String sql = "SELECT id, name FROM customers " +
                "WHERE name = '" + name + "'";
        return getCst(sql);
    }

    @Override
    public List<Project> getProjects(Customer customer) {
        connectToDB();
        String sql = "SELECT id, name, managerid, customerid " +
                "FROM projects " +
                "WHERE customerid = " + customer.getId();
        return helpForGetProjects(sql);
    }

    @Override
    public boolean insert(Customer customer) {
        connectToDB();
        String sql = "INSERT INTO customers " +
                "VALUES (" + customer.getId() + ", " + customer.getName() + ")";
        return helpForDeleteAndInsert(sql);
    }

    @Override
    public boolean delete(Customer customer) {
        connectToDB();
        String sql = "DELETE FROM customers " +
                "WHERE id =" + customer.getId();
        return helpForDeleteAndInsert(sql);
    }

    private Customer getCst(String sql) {
        Customer customer = null;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                customer = new Customer(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        if (customer == null) {
            try {
                throw new WrongInputData("Wrong data!");
            } catch (WrongInputData wrongInputData) {
                System.out.println("Wrong data!");
            }
        }
        return customer;
    }
}
