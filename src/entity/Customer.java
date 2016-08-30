package entity;

import java.util.ArrayList;

public class Customer extends Human {
    private static Integer idInc = 1;

    public Customer(String name) {
        super(idInc, name);
        ++idInc;
    }

    public Customer(Integer id, String name) {
        super(id, name);
    }

}
