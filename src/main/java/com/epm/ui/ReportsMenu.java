package com.epm.ui;

import com.epm.reports.ReportDAO;
import com.epm.util.InputHelper;

import java.sql.SQLException;

public class ReportsMenu {
    private final ReportDAO dao = new ReportDAO();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Reports ────────────────────────────────");
            System.out.println("   1. Employee with Department Details");
            System.out.println("   2. Employees Working on Projects");
            System.out.println("   3. Project-wise Employee Count");
            System.out.println("   4. Department-wise Salary Expense");
            System.out.println("   5. Highest Paid Employee");
            System.out.println("   6. Top 3 Highest Paid Employees");
            System.out.println("   7. Employees Not Assigned to Any Project");
            System.out.println("   8. Projects Without Employees");
            System.out.println("   9. Average Salary by Department");
            System.out.println("  10. Dashboard Summary");
            System.out.println("   0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case  1 -> dao.reportEmployeeWithDept();
                    case  2 -> dao.reportEmployeesOnProjects();
                    case  3 -> dao.reportProjectWiseCount();
                    case  4 -> dao.reportDeptSalaryExpense();
                    case  5 -> dao.reportHighestPaidEmployee();
                    case  6 -> dao.reportTop3HighestPaid();
                    case  7 -> dao.reportUnassignedEmployees();
                    case  8 -> dao.reportProjectsWithoutEmployees();
                    case  9 -> dao.reportAvgSalaryByDept();
                    case 10 -> dao.reportDashboard();
                    case  0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }
}
