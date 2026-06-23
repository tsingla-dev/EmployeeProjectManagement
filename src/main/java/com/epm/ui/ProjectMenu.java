package com.epm.ui;

import com.epm.model.Project;
import com.epm.service.ProjectService;
import com.epm.util.InputHelper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProjectMenu {
    private final ProjectService svc = new ProjectService();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Project Management ─────────────────────");
            System.out.println("  1. Add Project");
            System.out.println("  2. View All Projects");
            System.out.println("  3. Update Project");
            System.out.println("  4. Delete Project");
            System.out.println("  0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case 1 -> addProj();
                    case 2 -> viewAll();
                    case 3 -> updateProj();
                    case 4 -> deleteProj();
                    case 0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }

    private void printHeader() {
        System.out.printf("%n| %-5s | %-25s | %15s | %-12s |%n",
                "ID","Project Name","Budget","Start Date");
        System.out.println("|-------|---------------------------|-----------------|--------------|");
    }

    private void addProj() throws SQLException {
        String name   = InputHelper.readString("  Project name: ");
        double budget = InputHelper.readDouble("  Budget: ");
        LocalDate sd  = InputHelper.readDate("  Start date");
        Project p = svc.addProject(name, budget, sd);
        System.out.println("  ✅ Project added – ID: " + p.getProjectId());
    }

    private void viewAll() throws SQLException {
        List<Project> list = svc.getAllProjects();
        if (list.isEmpty()) { System.out.println("  No projects found."); return; }
        printHeader();
        list.forEach(System.out::println);
    }

    private void updateProj() throws SQLException {
        int id = InputHelper.readInt("  Project ID to update: ");
        svc.getById(id).ifPresentOrElse(p -> {
            try {
                String name  = InputHelper.readString("  Name [" + p.getProjectName() + "]: ");
                String budStr= InputHelper.readString("  Budget [" + p.getBudget() + "]: ");
                String sdStr = InputHelper.readString("  Start date [" + p.getStartDate() + "] (yyyy-MM-dd): ");
                boolean ok = svc.updateProject(id,
                        name.isEmpty()   ? p.getProjectName()               : name,
                        budStr.isEmpty() ? p.getBudget().doubleValue()      : Double.parseDouble(budStr),
                        sdStr.isEmpty()  ? p.getStartDate()                  : LocalDate.parse(sdStr));
                System.out.println(ok ? "  ✅ Updated." : "  ⚠ No change.");
            } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }
        }, () -> System.out.println("  ⚠ Project not found."));
    }

    private void deleteProj() throws SQLException {
        int id = InputHelper.readInt("  Project ID to delete: ");
        boolean ok = svc.deleteProject(id);
        System.out.println(ok ? "  ✅ Deleted." : "  ⚠ ID not found.");
    }
}
