package com.epm.service;

import com.epm.dao.DepartmentDAO;
import com.epm.model.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final DepartmentDAO dao = new DepartmentDAO();

    public Department addDepartment(String name) throws SQLException {
        Department d = new Department();
        d.setDepartmentName(name.trim());
        dao.addDepartment(d);
        return d;
    }

    public List<Department> getAllDepartments() throws SQLException {
        return dao.getAllDepartments();
    }

    public Optional<Department> getDepartmentById(int id) throws SQLException {
        return dao.getDepartmentById(id);
    }

    public boolean updateDepartment(int id, String newName) throws SQLException {
        Department d = new Department(id, newName.trim());
        return dao.updateDepartment(d);
    }

    public boolean deleteDepartment(int id) throws SQLException {
        return dao.deleteDepartment(id);
    }
}
