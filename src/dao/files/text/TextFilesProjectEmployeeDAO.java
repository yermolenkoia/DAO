package dao.files.text;

import dao.ProjectEmployeeDAO;
import entity.Employee;
import entity.Project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.files.text.TextFilesConst.*;
import static dao.files.text.TextFilesConst.PATH_TO_TMP;

/**
 * Created by IGOR on 20.03.2016.
 */
public class TextFilesProjectEmployeeDAO implements ProjectEmployeeDAO {
    private String pathToFile;
    private Scanner scannerForFile;

    public TextFilesProjectEmployeeDAO() {
        this.pathToFile = TextFilesConst.PATH + "ProjectEmployee.txt";
    }

    @Override
    public List<Integer> findByProjectId(Integer projectId) {
        ArrayList<Integer> employeesIds = new ArrayList<>();
        try (Scanner scannerForFile = new Scanner(new File(pathToFile))) {
            while (scannerForFile.hasNextLine()) {
                Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
                scannerForLine.useDelimiter(" ");
                if (scannerForLine.hasNext()) {
                    if (Integer.parseInt(scannerForLine.next()) == projectId) {
                        employeesIds.add(Integer.parseInt(scannerForLine.next()));
                    }
                }
                scannerForLine.close();
            }
            scannerForFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeesIds;
    }

    @Override
    public List<Integer> findByEmployeeId(Integer employeesId) {
        ArrayList<Integer> projectIds = new ArrayList<>();
        try (Scanner scannerForFile = new Scanner(new File(pathToFile))) {
            while (scannerForFile.hasNextLine()) {
                Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
                scannerForLine.useDelimiter(" ");
                if (scannerForLine.hasNext()) {
                    Integer tmp = Integer.parseInt(scannerForLine.next());
                    if (Integer.parseInt(scannerForLine.next()) == employeesId) {
                        projectIds.add(tmp);
                    }
                }
                scannerForLine.close();
            }
            scannerForFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectIds;
    }
    void delete(Employee employee){
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Integer projectId = Integer.parseInt(scannerForFile.next());
                Integer employeeId = Integer.parseInt(scannerForFile.next());
                if (!scannerForFile.hasNextLine()){
                    fw.close();
                    break;
                } else if(employeeId == employee.getId()){
                    continue;
                } else {
                    fw.write(projectId.toString()+ " " + employeeId + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
        scannerForFile.close();
        rewriteFile(PATH_TO_TMP, pathToFile);
    }
    void delete(Project project){
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Integer projectId = Integer.parseInt(scannerForFile.next());
                if (projectId == null){
                    fw.close();
                    break;
                } else if(projectId.equals(project.getId())){
                    scannerForFile.next();
                    continue;
                } else {
                    fw.write(projectId.toString()+ " " + scannerForFile.next() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
        scannerForFile.close();
        rewriteFile(PATH_TO_TMP, pathToFile);
    }
    void add(Integer projectId, Integer employeeId){
        if (findByProjectId(projectId).contains(employeeId)) return;
        String tmp = "" + projectId + " " + employeeId + "\n";
        try {
            Files.write(Paths.get(pathToFile), tmp.toString().getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
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
