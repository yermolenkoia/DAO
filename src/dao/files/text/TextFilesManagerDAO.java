package dao.files.text;

import dao.ManagerDAO;
import entity.Customer;
import entity.Manager;
import entity.Project;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.files.text.TextFilesConst.*;

/**
 * Created by IGOR on 19.02.2016.
 */
public class TextFilesManagerDAO implements ManagerDAO {
    private String pathToFile;
    private Scanner scannerForFile;

    public TextFilesManagerDAO() {
        this.pathToFile = TextFilesConst.PATH + "Managers.txt";
    }

    @Override
    public Manager findById(Integer id) {
        initScanner(pathToFile);
        while (true) {
            Manager tmpManager = getManagerFromFile();
            if (tmpManager == null) {
                scannerForFile.close();
                return null;
            } else if (tmpManager.getId().equals(id)) {
                scannerForFile.close();
                return tmpManager;
            }
        }

    }

    @Override
    public Manager findByName(String name) {
        initScanner(pathToFile);
        while (true) {
            Manager tmpManager = getManagerFromFile();
            if (tmpManager == null) {
                scannerForFile.close();
                return null;
            } else if (tmpManager.getName().equals(name)) {
                scannerForFile.close();
                return tmpManager;
            }
        }
    }

    @Override
    public List<Project> getProjects(Manager manager) {
        TextFilesProjectDAO projectDAO = new TextFilesProjectDAO();
        ArrayList<Project> projectList = new ArrayList<>();
        initScanner(PATH + "Projects.txt");
        while (true) {
            Project project = projectDAO.getProjectFromFile();
            if (project == null) {
                scannerForFile.close();
                break;
            } else if (project.getManager().getId().equals(manager.getId())) {
                projectList.add(project);
            }
        }
        return projectList;
    }

    @Override
    public boolean insert(Manager manager) {
        return standardInsert(pathToFile, manager);
    }

    @Override
    public boolean delete(Manager manager) {
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Manager tmpManager = getManagerFromFile();
                if (tmpManager == null){
                    fw.close();
                    break;
                } else if(tmpManager.equals(manager)){
                    continue;
                } else {
                    fw.write(tmpManager.toString()+"\n");
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

    private Manager getManagerFromFile() {
        try {
            while (scannerForFile.hasNextLine()) {
                Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
                scannerForLine.useDelimiter(" ");
                if (scannerForLine.hasNext()) {
                    Integer id = Integer.parseInt(scannerForLine.next());
                    String name = scannerForLine.next();
                    scannerForLine.close();
                    return new Manager(id, name);
                }
            }
        }catch (Exception e){
            System.out.println("Error has occurred");
        }
        return null;
    }
    void initScanner(String pathToFile) {
        try {
            scannerForFile = new Scanner(new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
