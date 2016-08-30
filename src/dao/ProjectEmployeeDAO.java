package dao;

import entity.Employee;
import entity.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IGOR on 26.02.2016.
 */
public interface ProjectEmployeeDAO {
    List<Integer> findByProjectId(Integer projectId);

    List<Integer> findByEmployeeId(Integer employeeId);
}
