package com.epm.ui;

import com.epm.model.Employee;
import com.epm.service.EmployeeService;
import com.epm.util.InputHelper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeMenu {
    private final EmployeeService svc = new EmployeeService();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Employee Management ────────────────────");
            System.out.println("  1. Add Employee");
            System.out.println("  2. View All Employees");
            System.out.println("  3. Search by ID");
            System.out.println("  4. Search by Email");
            System.out.println("  5. Update Employee");
            System.out.println("  6. Delete Employee");
            System.out.println("  0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case 1 -> addEmp();
                    case 2 -> viewAll();
                    case 3 -> searchById();
                    case 4 -> searchByEmail();
                    case 5 -> updateEmp();
                    case 6 -> deleteEmp();
                    case 0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }

    private void printHeader() {
        System.out.printf("%n| %-5s | %-20s | %-25s | %12s | %-12s | %-20s |%n",
                "ID","Name","Email","Salary","Joined","Department");
        System.out.println("|-------|----------------------|---------------------------|--------------|--------------|----------------------|");
    }

    private void addEmp() throws SQLException {
        String name   = InputHelper.readString("  Name: ");
        String email  = InputHelper.readString("  Email: ");
        double salary = InputHelper.readDouble("  Salary: ");
        LocalDate doj = InputHelper.readDate("  Joining date");
        int deptId    = InputHelper.readInt("  Department ID: ");
        Employee e = svc.addEmployee(name, email, salary, doj, deptId);
        System.out.println("  ✅ Employee added – ID: " + e.getEmployeeId());
    }

    private void viewAll() throws SQLException {
        List<Employee> list = svc.getAllEmployees();
        if (list.isEmpty()) { System.out.println("  No employees found."); return; }
        printHeader();
        list.forEach(System.out::println);
    }

    private void searchById() throws SQLException {
        int id = InputHelper.readInt("  Employee ID: ");
        svc.getById(id).ifPresentOrElse(e -> { printHeader(); System.out.println(e); },
                () -> System.out.println("  ⚠ Not found."));
    }

    private void searchByEmail() throws SQLException {
        String email = InputHelper.readString("  Email: ");
        svc.getByEmail(email).ifPresentOrElse(e -> { printHeader(); System.out.println(e); },
                () -> System.out.println("  ⚠ Not found."));
    }

    private void updateEmp() throws SQLException {
        int id = InputHelper.readInt("  Employee ID to update: ");
        svc.getById(id).ifPresentOrElse(e -> {
            try {
                String name   = InputHelper.readString("  Name [" + e.getName() + "]: ");
                String email  = InputHelper.readString("  Email [" + e.getEmail() + "]: ");
                String salStr = InputHelper.readString("  Salary [" + e.getSalary() + "]: ");
                String dojStr = InputHelper.readString("  Joining date [" + e.getJoiningDate() + "] (yyyy-MM-dd): ");
                String deptStr= InputHelper.readString("  Dept ID [" + e.getDepartmentId() + "]: ");

                String finalName  = name.isEmpty()   ? e.getName()               : name;
                String finalEmail = email.isEmpty()  ? e.getEmail()              : email;
                double finalSal   = salStr.isEmpty() ? e.getSalary().doubleValue(): Double.parseDouble(salStr);
                LocalDate finalDoj= dojStr.isEmpty() ? e.getJoiningDate()         : LocalDate.parse(dojStr);
                int finalDept     = deptStr.isEmpty()? e.getDepartmentId()         : Integer.parseInt(deptStr);

                boolean ok = svc.updateEmployee(id, finalName, finalEmail, finalSal, finalDoj, finalDept);
                System.out.println(ok ? "  ✅ Updated." : "  ⚠ No change.");
            } catch (Exception ex) { System.out.println("  ❌ " + ex.getMessage()); }
        }, () -> System.out.println("  ⚠ Employee not found."));
    }

    private void deleteEmp() throws SQLException {
        int id = InputHelper.readInt("  Employee ID to delete: ");
        boolean ok = svc.deleteEmployee(id);
        System.out.println(ok ? "  ✅ Deleted." : "  ⚠ ID not found.");
    }
}
