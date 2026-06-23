package com.epm.dao;

import com.epm.model.Project;
import com.epm.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAO {

    private Project mapRow(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setProjectId(rs.getInt("project_id"));
        p.setProjectName(rs.getString("project_name"));
        p.setBudget(rs.getBigDecimal("budget"));
        p.setStartDate(rs.getDate("start_date").toLocalDate());
        return p;
    }

    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO Project (project_name, budget, start_date) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, project.getProjectName());
            ps.setBigDecimal(2, project.getBudget());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) project.setProjectId(keys.getInt(1));
            }
        }
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM Project ORDER BY project_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public Optional<Project> getProjectById(int id) throws SQLException {
        String sql = "SELECT * FROM Project WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public boolean updateProject(Project project) throws SQLException {
        String sql = "UPDATE Project SET project_name=?, budget=?, start_date=? WHERE project_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, project.getProjectName());
            ps.setBigDecimal(2, project.getBudget());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setInt(4, project.getProjectId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM Project WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
