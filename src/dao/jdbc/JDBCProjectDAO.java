package dao.jdbc;

import dao.ProjectDAO;
import entity.Employee;
import entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 19.02.2016.
 */
public class JDBCProjectDAO extends JDBCConstants implements ProjectDAO {

    @Override
    public Project findById(Integer id) {
        connectToDB();
        String sql = "SELECT id, name, managerid, customerid FROM projects " +
                "WHERE id = " + id;
        return getProj(sql);
    }

    @Override
    public Project findByName(String name) {
        connectToDB();
        String sql = "SELECT id, name, managerid, customerid FROM projects " +
                "WHERE name = '" + name + "'";
        return getProj(sql);
    }

    @Override
    public List<Employee> getEmployees(Project project) {
        connectToDB();
        JDBCEmployeeDAO jdbcEmployeeDAO = new JDBCEmployeeDAO();
        ArrayList<Employee> tmp = new ArrayList<>();
        String sql = "SELECT employees.id, employees.name, employees.department " +
                "FROM employees " +
                "INNER JOIN project_employee " +
                "ON project_employee.employeeid = employees.id " +
                "AND project_employee.projectid = " + project.getId();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tmp.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        return tmp;
    }

    @Override
    public boolean insert(Project project) {
        connectToDB();
        String sql = "INSERT INTO projects " +
                "VALUES (" + project.getId() + ", '" + project.getName() + "', " + project.getManager().getId() + ", " +
                project.getCustomer().getId() + ")";
        try {
            stmt.executeUpdate(sql);
            if (!project.getEmployees().equals(null)) {
                for (Employee employee : project.getEmployees()) {
                    sql = "INSERT INTO PROJECT_EMPLOYEE " +
                            "VALUES (" + project.getId() + ", " + employee.getId() + ")";
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return false;
        }
        disconnectFromDB();
        return true;
    }

    @Override
    public boolean delete(Project project) {
        connectToDB();
        String sql2 = "DELETE FROM project_employee WHERE projectid = " + project.getId();
        String sql = "DELETE FROM projects " +
                "WHERE id =" + project.getId();
        return helpForDeleteAndInsert(sql2, sql);
    }

    @Override
    public void addEmployees(Project project, Employee... employees) {
        connectToDB();
        try {
            for (Employee employee : employees) {
                String sql = "INSERT INTO PROJECT_EMPLOYEE " +
                        "VALUES (" + project.getId() + ", " + employee.getId() + ")";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnectFromDB();
    }

    private Project getProj(String sql) {
        Project project = null;
        JDBCCustomerDAO jdbcCustomerDAO = new JDBCCustomerDAO();
        JDBCManagerDAO jdbcManagerDAO = new JDBCManagerDAO();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                project = new Project(rs.getInt("id"), jdbcManagerDAO.findById(rs.getInt("managerid")),
                        rs.getString("name"), jdbcCustomerDAO.findById(rs.getInt("customerid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        if (project == null) {
            try {
                throw new WrongInputData("Wrong data!");
            } catch (WrongInputData wrongInputData) {
                System.out.println("Wrong data!");
            }
        } else {
            project.setEmployees((ArrayList<Employee>) getEmployees(project));
        }
        return project;
    }
}
