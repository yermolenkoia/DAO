import dao.collections.CollectionsCustomerDAO;
import dao.collections.CollectionsEmployeeDAO;
import dao.collections.CollectionsManagerDAO;
import dao.collections.CollectionsProjectDAO;
import dao.collections.data.Data;
import dao.files.text.*;
import dao.jdbc.*;
import entity.Customer;
import entity.Employee;
import entity.Manager;
import entity.Project;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import quires.Quires;

import javax.annotation.processing.SupportedSourceVersion;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGOR on 25.02.2016.
 */
public class Main {
    public static void main(String[] args) {
//        CollectionsProjectDAO projectDAO = new CollectionsProjectDAO(dataBase.getProjects());
//        CollectionsCustomerDAO customerDAO = new CollectionsCustomerDAO(dataBase.getCustomers());
//        CollectionsManagerDAO managerDAO = new CollectionsManagerDAO(dataBase.getManagers());
//        CollectionsEmployeeDAO employeeDAO = new CollectionsEmployeeDAO(dataBase.getEmployees());
        TextFilesCustomerDAO customerDAO = new TextFilesCustomerDAO();
        TextFilesProjectEmployeeDAO projectEmployeeDAO = new TextFilesProjectEmployeeDAO();
        TextFilesManagerDAO managerDAO = new TextFilesManagerDAO();
        TextFilesProjectDAO projectDAO = new TextFilesProjectDAO();
        TextFilesEmployeeDAO employeeDAO = new TextFilesEmployeeDAO();
        Quires quires = new Quires(employeeDAO, projectDAO, managerDAO, customerDAO);
        Employee employee = employeeDAO.findById(2);
        System.out.print(quires.getEmployeesWithoutProjects());
    }
}

