package com.epm.service;

import com.epm.dao.EmployeeProjectDAO;
import com.epm.model.Employee;
import com.epm.model.Project;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AssignmentService {
    private final EmployeeProjectDAO dao = new EmployeeProjectDAO();

    public void assign(int empId, int projId, LocalDate date) throws SQLException {
        dao.assignEmployeeToProject(empId, projId, date);
    }

    public boolean remove(int empId, int projId) throws SQLException {
        return dao.removeEmployeeFromProject(empId, projId);
    }

    public List<Employee> getEmployeesByProject(int projId) throws SQLException {
        return dao.getEmployeesByProject(projId);
    }

    public List<Project> getProjectsByEmployee(int empId) throws SQLException {
        return dao.getProjectsByEmployee(empId);
    }
}
