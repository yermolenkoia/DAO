package dao.files.text;

import dao.CustomerDAO;
import entity.Customer;
import entity.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.files.text.TextFilesConst.*;
import static dao.files.text.TextFilesConst.PATH_TO_TMP;
import static dao.files.text.TextFilesConst.rewriteFile;

/**
 * Created by IGOR on 18.02.2016.
 */
public class TextFilesCustomerDAO implements CustomerDAO {
    private String pathToFile;
    private Scanner scannerForFile;

    public TextFilesCustomerDAO() {
        this.pathToFile = TextFilesConst.PATH + "Customers.txt";
    }

    @Override
    public Customer findById(Integer id) {
        initScanner(pathToFile);
        while (true) {
            Customer tmpCustomer = getCustomerFromFile();
            if (tmpCustomer == null) {
                scannerForFile.close();
                return null;
            } else if (tmpCustomer.getId().equals(id)) {
                scannerForFile.close();
                return tmpCustomer;
            }
        }
    }

    @Override
    public Customer findByName(String name) {
        initScanner(pathToFile);
        while (true) {
            Customer tmpCustomer = getCustomerFromFile();
            if (tmpCustomer == null) {
                scannerForFile.close();
                return null;
            } else if (tmpCustomer.getName().equals(name)) {
                scannerForFile.close();
                return tmpCustomer;
            }
        }
    }

    @Override
    public List<Project> getProjects(Customer customer) {
        TextFilesProjectDAO projectDAO = new TextFilesProjectDAO();
        ArrayList<Project> projectList = new ArrayList<>();
        initScanner(PATH + "Projects.txt");
        while (true) {
            Project project = projectDAO.getProjectFromFile();
            if (project == null) {
                scannerForFile.close();
                break;
            } else if (project.getCustomer().getId().equals(customer.getId())) {
                projectList.add(project);
            }
        }
        return projectList;
    }

    @Override
    public boolean insert(Customer customer) {
        return standardInsert(pathToFile, customer);
    }

    @Override
    public boolean delete(Customer customer) {
        initScanner(pathToFile);
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(PATH_TO_TMP);
            while (true){
                Customer tmpCustomer = getCustomerFromFile();
                if (tmpCustomer == null){
                    fw.close();
                    break;
                } else if(tmpCustomer.equals(customer)){
                    continue;
                } else {
                    fw.write(tmpCustomer.toString()+"\n");
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

    private Customer getCustomerFromFile() {
        while (scannerForFile.hasNextLine()) {
            Scanner scannerForLine = new Scanner(scannerForFile.nextLine());
            scannerForLine.useDelimiter(" ");
            if (scannerForLine.hasNext()) {
                Integer id = Integer.parseInt(scannerForLine.next());
                String name = scannerForLine.next();
                scannerForLine.close();
                return new Customer(id, name);
            }
        }
        return null;
    }
    private void initScanner(String pathToFile) {
        try {
            scannerForFile = new Scanner(new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
