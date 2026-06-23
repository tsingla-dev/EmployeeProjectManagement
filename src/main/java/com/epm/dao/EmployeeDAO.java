package com.epm.dao;

import com.epm.model.Employee;
import com.epm.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO {

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(rs.getInt("employee_id"));
        e.setName(rs.getString("name"));
        e.setEmail(rs.getString("email"));
        e.setSalary(rs.getBigDecimal("salary"));
        e.setJoiningDate(rs.getDate("joining_date").toLocalDate());
        e.setDepartmentId(rs.getInt("department_id"));
        try { e.setDepartmentName(rs.getString("department_name")); }
        catch (SQLException ignored) {}
        return e;
    }

    public void addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO Employee (name, email, salary, joining_date, department_id) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setBigDecimal(3, emp.getSalary());
            ps.setDate(4, Date.valueOf(emp.getJoiningDate()));
            ps.setInt(5, emp.getDepartmentId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) emp.setEmployeeId(keys.getInt(1));
            }
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = """
            SELECT e.*, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            ORDER BY e.employee_id
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public Optional<Employee> getEmployeeById(int id) throws SQLException {
        String sql = """
            SELECT e.*, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            WHERE e.employee_id = ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public Optional<Employee> getEmployeeByEmail(String email) throws SQLException {
        String sql = """
            SELECT e.*, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            WHERE e.email = ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public boolean updateEmployee(Employee emp) throws SQLException {
        String sql = "UPDATE Employee SET name=?, email=?, salary=?, joining_date=?, department_id=? WHERE employee_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setBigDecimal(3, emp.getSalary());
            ps.setDate(4, Date.valueOf(emp.getJoiningDate()));
            ps.setInt(5, emp.getDepartmentId());
            ps.setInt(6, emp.getEmployeeId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
