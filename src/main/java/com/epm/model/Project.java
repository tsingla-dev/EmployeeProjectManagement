package com.epm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Project {
    private int        projectId;
    private String     projectName;
    private BigDecimal budget;
    private LocalDate  startDate;

    public Project() {}

    public int        getProjectId()   { return projectId; }
    public String     getProjectName() { return projectName; }
    public BigDecimal getBudget()      { return budget; }
    public LocalDate  getStartDate()   { return startDate; }

    public void setProjectId(int projectId)       { this.projectId   = projectId; }
    public void setProjectName(String projectName){ this.projectName = projectName; }
    public void setBudget(BigDecimal budget)      { this.budget      = budget; }
    public void setStartDate(LocalDate startDate) { this.startDate   = startDate; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-25s | %15.2f | %-12s |",
                projectId, projectName, budget, startDate);
    }
}
