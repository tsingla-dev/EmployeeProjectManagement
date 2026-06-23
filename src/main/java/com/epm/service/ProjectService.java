package com.epm.service;

import com.epm.dao.ProjectDAO;
import com.epm.model.Project;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProjectService {
    private final ProjectDAO dao = new ProjectDAO();

    public Project addProject(String name, double budget, LocalDate startDate) throws SQLException {
        Project p = new Project();
        p.setProjectName(name.trim());
        p.setBudget(BigDecimal.valueOf(budget));
        p.setStartDate(startDate);
        dao.addProject(p);
        return p;
    }

    public List<Project> getAllProjects() throws SQLException { return dao.getAllProjects(); }

    public Optional<Project> getById(int id) throws SQLException { return dao.getProjectById(id); }

    public boolean updateProject(int id, String name, double budget, LocalDate startDate) throws SQLException {
        Project p = new Project();
        p.setProjectId(id);
        p.setProjectName(name.trim());
        p.setBudget(BigDecimal.valueOf(budget));
        p.setStartDate(startDate);
        return dao.updateProject(p);
    }

    public boolean deleteProject(int id) throws SQLException { return dao.deleteProject(id); }
}
