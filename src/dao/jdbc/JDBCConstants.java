package dao.jdbc;

import entity.Customer;
import entity.Employee;
import entity.Manager;
import entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

abstract class JDBCConstants {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "IHOR";
    private static final String PASS = "999666";
    protected static Connection conn;
    protected static Statement stmt;

    protected static void connectToDB() {
        try {
            Class.forName(JDBCConstants.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(JDBCConstants.DB_URL, JDBCConstants.USER, JDBCConstants.PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected static void disconnectFromDB() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    protected boolean helpForDeleteAndInsert(String ... sql){
        try {
            for (String str : sql) {
                stmt.executeUpdate(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectFromDB();
            return false;
        }
        disconnectFromDB();
        return true;
    }
    protected ArrayList<Project> helpForGetProjects(String sql){
        JDBCManagerDAO jdbcManagerDAO = new JDBCManagerDAO();
        JDBCCustomerDAO jdbcCustomerDAO = new JDBCCustomerDAO();
        ArrayList<Project> tmp = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                tmp.add(new Project(rs.getInt("id"), jdbcManagerDAO.findById(rs.getInt("managerid")),
                        rs.getString("name"), jdbcCustomerDAO.findById(rs.getInt("customerid"))));
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
class WrongInputData extends Exception{
    WrongInputData(){}
    WrongInputData(String message){
        super(message);
    }
}
