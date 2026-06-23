package com.epm.dao;

import com.epm.model.Department;
import com.epm.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDAO {

    public void addDepartment(Department dept) throws SQLException {
        String sql = "INSERT INTO Department (department_name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dept.getDepartmentName());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) dept.setDepartmentId(keys.getInt(1));
            }
        }
    }

    public List<Department> getAllDepartments() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT department_id, department_name FROM Department ORDER BY department_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Department d = new Department();
                d.setDepartmentId(rs.getInt("department_id"));
                d.setDepartmentName(rs.getString("department_name"));
                list.add(d);
            }
        }
        return list;
    }

    public Optional<Department> getDepartmentById(int id) throws SQLException {
        String sql = "SELECT department_id, department_name FROM Department WHERE department_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department d = new Department();
                    d.setDepartmentId(rs.getInt("department_id"));
                    d.setDepartmentName(rs.getString("department_name"));
                    return Optional.of(d);
                }
            }
        }
        return Optional.empty();
    }

    public boolean updateDepartment(Department dept) throws SQLException {
        String sql = "UPDATE Department SET department_name = ? WHERE department_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dept.getDepartmentName());
            ps.setInt(2, dept.getDepartmentId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteDepartment(int id) throws SQLException {
        String sql = "DELETE FROM Department WHERE department_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
