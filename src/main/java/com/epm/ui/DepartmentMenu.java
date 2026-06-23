package com.epm.ui;

import com.epm.model.Department;
import com.epm.service.DepartmentService;
import com.epm.util.InputHelper;
import java.sql.SQLException;
import java.util.List;

public class DepartmentMenu {
    private final DepartmentService svc = new DepartmentService();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Department Management ──────────────────");
            System.out.println("  1. Add Department");
            System.out.println("  2. View All Departments");
            System.out.println("  3. Update Department");
            System.out.println("  4. Delete Department");
            System.out.println("  0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case 1 -> addDept();
                    case 2 -> viewAll();
                    case 3 -> updateDept();
                    case 4 -> deleteDept();
                    case 0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }

    private void addDept() throws SQLException {
        String name = InputHelper.readString("  Department name: ");
        Department d = svc.addDepartment(name);
        System.out.println("  ✅ Added – ID: " + d.getDepartmentId());
    }

    private void viewAll() throws SQLException {
        List<Department> list = svc.getAllDepartments();
        if (list.isEmpty()) { System.out.println("  No departments found."); return; }
        System.out.printf("%n| %-5s | %-30s |%n", "ID", "Department Name");
        System.out.println("|-------|--------------------------------|");
        list.forEach(System.out::println);
    }

    private void updateDept() throws SQLException {
        int id = InputHelper.readInt("  Department ID to update: ");
        svc.getDepartmentById(id).ifPresentOrElse(d -> {
            try {
                String name = InputHelper.readString("  New name [" + d.getDepartmentName() + "]: ");
                if (name.isEmpty()) name = d.getDepartmentName();
                boolean ok = svc.updateDepartment(id, name);
                System.out.println(ok ? "  ✅ Updated." : "  ⚠ No change made.");
            } catch (SQLException e) { System.out.println("  ❌ " + e.getMessage()); }
        }, () -> System.out.println("  ⚠ Department not found."));
    }

    private void deleteDept() throws SQLException {
        int id = InputHelper.readInt("  Department ID to delete: ");
        try {
            boolean ok = svc.deleteDepartment(id);
            System.out.println(ok ? "  ✅ Deleted." : "  ⚠ ID not found.");
        } catch (SQLException e) {
            System.out.println("  ❌ Cannot delete – employees may still reference this department.");
        }
    }
}
