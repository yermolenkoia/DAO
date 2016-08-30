package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IGOR on 19.02.2016.
 */
public abstract class Human {
    private Integer id;
    private String name;
    private ArrayList<Project> projects;

    Human(Integer id, String name, ArrayList<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    Human(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.projects = new ArrayList<Project>();
    }

    Human(Integer id, String name, Project... projects) {
        this.id = id;
        this.name = name;
        for (Project project : projects) {
            this.projects.add(project);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "" + id + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (!getId().equals(human.getId())) return false;
        if (!getName().equals(human.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
