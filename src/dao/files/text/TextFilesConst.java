package dao.files.text;

import entity.Human;
import entity.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * Created by IGOR on 20.03.2016.
 */
public class TextFilesConst {
    static final String PATH = "C:\\Users\\IGOR\\IdeaProjects\\laba3\\src\\dao\\files\\text\\files\\";
    static final String PATH_TO_TMP = PATH + "tmp.txt";
    static void rewriteFile(String input, String output){
        Scanner sc = null;
        PrintWriter printer = null;
        try {
            sc = new Scanner(new File(input));
            printer = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            printer.write(s + "\n");
        }
        printer.close();
        sc.close();
    }
    public static boolean standardInsert(String pathToFile, Human human){
        try {
            String tmp = human.toString() + "\n";
            Files.write(Paths.get(pathToFile), tmp.getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean standardInsert(String pathToFile, Project project){
        try {
            String tmp = project.toString() + "\n";
            Files.write(Paths.get(pathToFile), tmp.getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
