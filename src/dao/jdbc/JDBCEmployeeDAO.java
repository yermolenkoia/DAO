package dao.jdbc;

import dao.EmployeeDAO;
import entity.Employee;
import entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCEmployeeDAO extends JDBCConstants implements EmployeeDAO {
    @Override
    public Employee findById(Integer id) {
        connectToDB();
        String sql = "SELECT id, name, department FROM employees " +
                "WHERE id = " + id;
        return getEmp(sql);
    }

    @Override
    public Employee findByName(String name) {
        connectToDB();
        String sql = "SELECT id, name, department FROM employees " +
                "WHERE name = '" + name + "'";
        return getEmp(sql);
    }

    @Override
    public Employee findByDepartment(String department) {
        connectToDB();
        String sql = "SELECT id, name, department FROM employees " +
                "WHERE department = " + department;
        return getEmp(sql);
    }

    private Employee getEmp(String sql) {
        Employee employee = null;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        if (employee == null) {
            try {
                throw new WrongInputData("Wrong data!");
            } catch (WrongInputData wrongInputData) {
                System.out.println("Wrong data!");
            }
        } else {
            employee.setProjects((ArrayList<Project>) getProjects(employee));
        }
        return employee;
    }

    @Override
    public List<Project> getProjects(Employee employee) {
        connectToDB();
        String sql = "SELECT projects.id, projects.managerid, projects.name, projects.customerid " +
                "FROM projects " +
                "INNER JOIN project_employee " +
                "ON project_employee.projectid = projects.id " +
                "AND project_employee.employeeid = " + employee.getId();
        return helpForGetProjects(sql);
    }

    @Override
    public List<Employee> getEmpsWithoutProject() {
        String sql = "SELECT id " +
                "FROM employees " +
                "MINUS " +
                "SELECT employeeid " +
                "FROM project_employee";
        ArrayList<Employee> employeesList = new ArrayList<>();
        connectToDB();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employeesList.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        return employeesList;
    }

    @Override
    public boolean insert(Employee employee) {
        connectToDB();
        String sql = "INSERT INTO employees (id, name, department) VALUES (" + employee.getId() + ", '" +
                employee.getName() + "', '" + employee.getDepartment() + "')";
        try {
            stmt.executeUpdate(sql);
            for (Project project : employee.getProjects()) {
                sql = "INSERT INTO PROJECT_EMPLOYEE " +
                        "VALUES (" + project.getId() + ", " + employee.getId() + ")";
                stmt.executeUpdate(sql);
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
    public boolean delete(Employee employee) {
        connectToDB();
        String sql = "DELETE FROM employees " +
                "WHERE id =" + employee.getId();
        String sql2 = "DELETE FROM project_employee WHERE employeeid = " + employee.getId();
        return helpForDeleteAndInsert(sql2, sql);
    }

    @Override
    public void addProjects(Employee employee, Project... projects) {
        connectToDB();
        try {
            for (Project project : projects) {
                String sql = "INSERT INTO PROJECT_EMPLOYEE " +
                        "VALUES (" + project.getId() + ", " + employee.getId() + ")";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnectFromDB();
    }
}
