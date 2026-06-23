package com.epm.service;

import com.epm.dao.EmployeeDAO;
import com.epm.model.Employee;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAO();

    public Employee addEmployee(String name, String email, double salary,
                                LocalDate joiningDate, int deptId) throws SQLException {
        Employee e = new Employee();
        e.setName(name.trim());
        e.setEmail(email.trim());
        e.setSalary(BigDecimal.valueOf(salary));
        e.setJoiningDate(joiningDate);
        e.setDepartmentId(deptId);
        dao.addEmployee(e);
        return e;
    }

    public List<Employee> getAllEmployees() throws SQLException { return dao.getAllEmployees(); }

    public Optional<Employee> getById(int id) throws SQLException { return dao.getEmployeeById(id); }

    public Optional<Employee> getByEmail(String email) throws SQLException { return dao.getEmployeeByEmail(email); }

    public boolean updateEmployee(int id, String name, String email, double salary,
                                  LocalDate joiningDate, int deptId) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(id);
        e.setName(name.trim());
        e.setEmail(email.trim());
        e.setSalary(BigDecimal.valueOf(salary));
        e.setJoiningDate(joiningDate);
        e.setDepartmentId(deptId);
        return dao.updateEmployee(e);
    }

    public boolean deleteEmployee(int id) throws SQLException { return dao.deleteEmployee(id); }
}
