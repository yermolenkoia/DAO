package entity;

import java.util.ArrayList;

public class Manager extends Human {
    private static Integer idInc = 1;

    public Manager(String name) {
        super(idInc, name);
        ++idInc;
    }

    public Manager(Integer id, String name) {
        super(id, name);
    }
}
