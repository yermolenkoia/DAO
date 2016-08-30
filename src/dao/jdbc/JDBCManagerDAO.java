package dao.jdbc;

import dao.ManagerDAO;
import entity.Customer;
import entity.Employee;
import entity.Manager;
import entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 19.02.2016.
 */
public class JDBCManagerDAO extends JDBCConstants implements ManagerDAO {


    @Override
    public Manager findById(Integer id) {
        connectToDB();
        String sql = "SELECT id, name FROM managers " +
                "WHERE id = " + id;
        return getMng(sql);
    }

    @Override
    public Manager findByName(String name) {
        connectToDB();
        String sql = "SELECT id, name FROM managers " +
                "WHERE name = '" + name + "'";
        return getMng(sql);
    }

    @Override
    public List<Project> getProjects(Manager manager) {
        connectToDB();
        String sql = "SELECT id, name, managerid, customerid " +
                "FROM projects " +
                "WHERE managerid = " + manager.getId();
        return helpForGetProjects(sql);
    }

    @Override
    public boolean insert(Manager manager) {
        connectToDB();
        String sql = "INSERT INTO managers " +
                "VALUES (" + manager.getId() + ", '" + manager.getName() + "')";
        return helpForDeleteAndInsert(sql);
    }

    @Override
    public boolean delete(Manager manager) {
        connectToDB();
        String sql = "DELETE FROM customers " +
                "WHERE id =" + manager.getId();
        return helpForDeleteAndInsert(sql);
    }

    private Manager getMng(String sql) {
        Manager manager = null;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                manager = new Manager(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        if (manager == null) {
            try {
                throw new WrongInputData("Wrong data!");
            } catch (WrongInputData wrongInputData) {
                System.out.println("Wrong data!");
            }
        }
        return manager;
    }
}
