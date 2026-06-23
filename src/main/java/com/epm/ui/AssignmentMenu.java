package com.epm.ui;

import com.epm.model.Employee;
import com.epm.model.Project;
import com.epm.service.AssignmentService;
import com.epm.util.InputHelper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AssignmentMenu {
    private final AssignmentService svc = new AssignmentService();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Employee-Project Assignments ───────────");
            System.out.println("  1. Assign Employee to Project");
            System.out.println("  2. Remove Employee from Project");
            System.out.println("  3. View Employees on a Project");
            System.out.println("  4. View Projects of an Employee");
            System.out.println("  0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case 1 -> assign();
                    case 2 -> remove();
                    case 3 -> empsByProject();
                    case 4 -> projsByEmployee();
                    case 0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }

    private void assign() throws SQLException {
        int empId  = InputHelper.readInt("  Employee ID: ");
        int projId = InputHelper.readInt("  Project ID: ");
        LocalDate date = InputHelper.readDate("  Assigned date");
        svc.assign(empId, projId, date);
        System.out.println("  ✅ Assigned successfully (transaction committed).");
    }

    private void remove() throws SQLException {
        int empId  = InputHelper.readInt("  Employee ID: ");
        int projId = InputHelper.readInt("  Project ID: ");
        boolean ok = svc.remove(empId, projId);
        System.out.println(ok ? "  ✅ Removed." : "  ⚠ Mapping not found.");
    }

    private void empsByProject() throws SQLException {
        int projId = InputHelper.readInt("  Project ID: ");
        List<Employee> list = svc.getEmployeesByProject(projId);
        if (list.isEmpty()) { System.out.println("  No employees assigned to this project."); return; }
        System.out.printf("%n| %-5s | %-20s | %-25s |%n", "ID","Name","Email");
        System.out.println("|-------|----------------------|---------------------------|");
        list.forEach(e -> System.out.printf("| %-5d | %-20s | %-25s |%n",
                e.getEmployeeId(), e.getName(), e.getEmail()));
    }

    private void projsByEmployee() throws SQLException {
        int empId = InputHelper.readInt("  Employee ID: ");
        List<Project> list = svc.getProjectsByEmployee(empId);
        if (list.isEmpty()) { System.out.println("  No projects assigned to this employee."); return; }
        System.out.printf("%n| %-5s | %-25s | %15s |%n", "ID","Project Name","Budget");
        System.out.println("|-------|---------------------------|-----------------|");
        list.forEach(p -> System.out.printf("| %-5d | %-25s | %15.2f |%n",
                p.getProjectId(), p.getProjectName(), p.getBudget()));
    }
}
