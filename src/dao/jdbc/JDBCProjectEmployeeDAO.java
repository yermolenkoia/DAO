package dao.jdbc;

import dao.ProjectEmployeeDAO;
import entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCProjectEmployeeDAO extends JDBCConstants implements ProjectEmployeeDAO {

    @Override
    public List<Integer> findByProjectId(Integer projectId) {
        connectToDB();
        ArrayList<Integer> tmp = new ArrayList<>();
        String sql = "SELECT employeeid FROM project_employee " +
                "WHERE projectid = " + projectId;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tmp.add(rs.getInt("employeeid"));
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
    public List<Integer> findByEmployeeId(Integer employeeId) {
        connectToDB();
        ArrayList<Integer> tmp = new ArrayList<>();
        String sql = "SELECT projectid FROM project_employee " +
                "WHERE employeeid = " + employeeId;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tmp.add(rs.getInt("projectid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return null;
        }
        disconnectFromDB();
        return tmp;
    }

}
