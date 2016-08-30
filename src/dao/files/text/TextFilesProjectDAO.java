package dao.files.text;

import dao.ProjectDAO;
import entity.Employee;
import entity.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.files.text.TextFilesConst.*;
import static dao.files.text.TextFilesConst.rewriteFile;

/**
 * Created by IGOR on 19.02.2016.
 */
public class TextFilesProjectDAO implements ProjectDAO {
    private String pathToFile;
    private TextFilesProjectEmployeeDAO projectEmployeeDAO;
    private Scanner scannerForFile;


    public TextFilesProjectDAO() {
        this.pathToFile = TextFilesConst.PATH + "Projects.txt";
        this.projectEmployeeDAO = new TextFilesProjectEmployeeDAO();

    }
    @Override
    public Project findById(Integer id) {
        initScanner(pathToFile);
        while (true) {
            Project project = getProjectFromFile();
            if (project == null) {
                scannerForFile.close();
                return null;
            } else if (project.getId().equals(id)) {
                scannerForFile.close();
                return project;
            }
        }
    }

    Project getProjectFromFile() {
        TextFilesManagerDAO managers = new TextFilesManagerDAO();
        TextFilesCustomerDAO customers = new TextFilesCustomerDAO();
        try {
            while (scannerForFile.hasNextLine()) {
                Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
                scannerForLine.useDelimiter(" ");
                if (scannerForLine.hasNext()) {
                    Integer id = Integer.parseInt(scannerForLine.next());
                    String name = scannerForLine.next();
                    Integer managerId = Integer.parseInt(scannerForLine.next());
                    Integer customerId = Integer.parseInt(scannerForLine.next());
                    scannerForLine.close();
                    return new Project(id, managers.findById(managerId), name, customers.findById(customerId));
                }
            }
        }catch (Exception e){
            System.out.println("Error has occurred");
        }
        return null;
    }

    @Override
    public Project findByName(String name) {
        initScanner(pathToFile);
        while (true) {
            Project project = getProjectFromFile();
            if (project == null) {
                scannerForFile.close();
                return null;
            } else if (project.getName().equals(name)) {
                scannerForFile.close();
                return project;
            }
        }    }

    @Override
    public List<Employee> getEmployees(Project project) {
        TextFilesEmployeeDAO employeeDAO = new TextFilesEmployeeDAO();
        ArrayList<Employee> employees = new ArrayList<>();
        for (Integer projectId : projectEmployeeDAO.findByEmployeeId(project.getId())){
            employees.add(employeeDAO.findById(projectId));
        }
        return employees;    }

    @Override
    public boolean insert(Project project) {
        boolean success = standardInsert(pathToFile, project);
        if (success){
            for (Employee employee: project.getEmployees()){
                projectEmployeeDAO.add(project.getId(), employee.getId());
            }
        }
        return success;
    }

    @Override
    public boolean delete(Project project) {
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Project tmpProject = getProjectFromFile();
                if (tmpProject == null){
                    fw.close();
                    break;
                } else if(tmpProject.equals(project)){
                    continue;
                } else {
                    fw.write(tmpProject.toString()+"\n");
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
    public void addEmployees(Project project, Employee... employees) {
        for (Employee employee: employees){
            projectEmployeeDAO.add(project.getId(), employee.getId());
        }
    }
    void initScanner(String pathToFile) {
        try {
            scannerForFile = new Scanner(new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
