package com.epm.dao;

import com.epm.model.Employee;
import com.epm.model.Project;
import com.epm.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeProjectDAO {

    /**
     * Assign an employee to a project inside a transaction.
     * Verifies both IDs exist before inserting the mapping row.
     */
    public void assignEmployeeToProject(int employeeId, int projectId, LocalDate assignedDate)
            throws SQLException {
        String checkEmp  = "SELECT employee_id FROM Employee WHERE employee_id = ?";
        String checkProj = "SELECT project_id  FROM Project  WHERE project_id  = ?";
        String insert    = "INSERT INTO EmployeeProject (employee_id, project_id, assigned_date) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verify employee exists
                try (PreparedStatement ps = conn.prepareStatement(checkEmp)) {
                    ps.setInt(1, employeeId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Employee ID " + employeeId + " not found.");
                    }
                }
                // Verify project exists
                try (PreparedStatement ps = conn.prepareStatement(checkProj)) {
                    ps.setInt(1, projectId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Project ID " + projectId + " not found.");
                    }
                }
                // Insert mapping
                try (PreparedStatement ps = conn.prepareStatement(insert)) {
                    ps.setInt(1, employeeId);
                    ps.setInt(2, projectId);
                    ps.setDate(3, Date.valueOf(assignedDate));
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean removeEmployeeFromProject(int employeeId, int projectId) throws SQLException {
        String sql = "DELETE FROM EmployeeProject WHERE employee_id=? AND project_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setInt(2, projectId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Employee> getEmployeesByProject(int projectId) throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = """
            SELECT e.employee_id, e.name, e.email, e.salary, e.joining_date,
                   e.department_id, d.department_name
            FROM EmployeeProject ep
            JOIN Employee e   ON ep.employee_id  = e.employee_id
            JOIN Department d ON e.department_id = d.department_id
            WHERE ep.project_id = ?
            ORDER BY e.name
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id"));
                    e.setName(rs.getString("name"));
                    e.setEmail(rs.getString("email"));
                    e.setSalary(rs.getBigDecimal("salary"));
                    e.setJoiningDate(rs.getDate("joining_date").toLocalDate());
                    e.setDepartmentId(rs.getInt("department_id"));
                    e.setDepartmentName(rs.getString("department_name"));
                    list.add(e);
                }
            }
        }
        return list;
    }

    public List<Project> getProjectsByEmployee(int employeeId) throws SQLException {
        List<Project> list = new ArrayList<>();
        String sql = """
            SELECT p.*
            FROM EmployeeProject ep
            JOIN Project p ON ep.project_id = p.project_id
            WHERE ep.employee_id = ?
            ORDER BY p.project_name
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Project p = new Project();
                    p.setProjectId(rs.getInt("project_id"));
                    p.setProjectName(rs.getString("project_name"));
                    p.setBudget(rs.getBigDecimal("budget"));
                    p.setStartDate(rs.getDate("start_date").toLocalDate());
                    list.add(p);
                }
            }
        }
        return list;
    }
}
