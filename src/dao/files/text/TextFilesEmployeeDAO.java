package dao.files.text;

import dao.EmployeeDAO;
import entity.Employee;
import entity.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.files.text.TextFilesConst.*;
import static dao.files.text.TextFilesConst.PATH_TO_TMP;
import static dao.files.text.TextFilesConst.rewriteFile;


/**
 * Created by IGOR on 19.02.2016.
 */
public class TextFilesEmployeeDAO implements EmployeeDAO {
    private String pathToFile;
    private Scanner scannerForFile;
    private TextFilesProjectEmployeeDAO projectEmployeeDAO;

    public TextFilesEmployeeDAO() {
        this.pathToFile = TextFilesConst.PATH + "Employees.txt";
        this.projectEmployeeDAO = new TextFilesProjectEmployeeDAO();

    }

    @Override
    public Employee findById(Integer id) {
        initScanner(pathToFile);
        while (true) {
            Employee employee = getEmployeeFromFile();
            if (employee == null) {
                scannerForFile.close();
                return null;
            } else if (employee.getId().equals(id)) {
                scannerForFile.close();
                return employee;
            }
        }
    }

    private Employee getEmployeeFromFile() {
        try {
            while (scannerForFile.hasNextLine()) {
                Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
                scannerForLine.useDelimiter(" ");
                if (scannerForLine.hasNext()) {
                    Integer id = Integer.parseInt(scannerForLine.next());
                    String name = scannerForLine.next();
                    String dept = scannerForLine.next();
                    scannerForLine.close();
                    return new Employee(id, name, dept);
                }
            }
        }catch (Exception e){
            System.out.println("Error has occurred");
        }
        return null;
    }

    @Override
    public Employee findByName(String name) {
        initScanner(pathToFile);
        while (true) {
            Employee employee = getEmployeeFromFile();
            if (employee == null) {
                scannerForFile.close();
                return null;
            } else if (employee.getName().equals(name)) {
                scannerForFile.close();
                return employee;
            }
        }
    }

    @Override
    public Employee findByDepartment(String department) {
        initScanner(pathToFile);
        while (true) {
            Employee employee = getEmployeeFromFile();
            if (employee == null) {
                scannerForFile.close();
                return null;
            } else if (employee.getDepartment().equals(department)) {
                scannerForFile.close();
                return employee;
            }
        }
    }

    @Override
    public List<Project> getProjects(Employee employee) {
        TextFilesProjectDAO projectDAO = new TextFilesProjectDAO();
        ArrayList<Project> projects = new ArrayList<>();
        for (Integer projectId : projectEmployeeDAO.findByEmployeeId(employee.getId())){
            projects.add(projectDAO.findById(projectId));
        }
        return projects;
    }

    @Override
    public List<Employee> getEmpsWithoutProject() {
        ArrayList<Employee> employees = new ArrayList<>();
        initScanner(pathToFile);
        while (true) {
            Employee employee = getEmployeeFromFile();
            if (employee == null) {
                break;
            } else if (getProjects(employee).size() == 0) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Override
    public boolean insert(Employee employee) {
        boolean success = standardInsert(pathToFile, employee);
        if (success){
            for (Project project : employee.getProjects()){
                projectEmployeeDAO.add(project.getId(), employee.getId());
            }
        }
        return success;
    }

    @Override
    public boolean delete(Employee employee) {
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Employee tmpEmployee = getEmployeeFromFile();
                if (tmpEmployee == null){
                    fw.close();
                    break;
                } else if(tmpEmployee.equals(employee)){
                    continue;
                } else {
                    fw.write(tmpEmployee.toString()+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
        scannerForFile.close();
        rewriteFile(PATH_TO_TMP, pathToFile);
        return true;
    }

    @Override
    public void addProjects(Employee employee, Project... projects) {
        for (Project project : projects){
            projectEmployeeDAO.add(project.getId(), employee.getId());
        }
    }
    private void initScanner(String pathToFile) {
        try {
            scannerForFile = new Scanner(new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
