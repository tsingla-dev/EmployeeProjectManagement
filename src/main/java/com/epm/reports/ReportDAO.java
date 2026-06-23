package com.epm.reports;

import com.epm.util.DBConnection;
import java.sql.*;

/**
 * All 10 report queries using SQL joins and aggregation.
 */
public class ReportDAO {

    // ── helper ─────────────────────────────────────────────────────────────
    private void printHR(String label) {
        System.out.println("\n" + "═".repeat(90));
        System.out.println("  " + label);
        System.out.println("═".repeat(90));
    }

    // 1. Employee with Department Details
    public void reportEmployeeWithDept() throws SQLException {
        printHR("REPORT 1 – Employee with Department Details");
        String sql = """
            SELECT e.employee_id, e.name, e.email, e.salary, e.joining_date, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            ORDER BY d.department_name, e.name
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-5s %-20s %-25s %12s %-12s %-20s%n",
                    "ID", "Name", "Email", "Salary", "Joined", "Department");
            System.out.println("-".repeat(90));
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-25s %12.2f %-12s %-20s%n",
                        rs.getInt("employee_id"), rs.getString("name"),
                        rs.getString("email"), rs.getBigDecimal("salary"),
                        rs.getDate("joining_date"), rs.getString("department_name"));
            }
        }
    }

    // 2. Employees Working on Projects
    public void reportEmployeesOnProjects() throws SQLException {
        printHR("REPORT 2 – Employees Working on Projects");
        String sql = """
            SELECT e.name AS employee, p.project_name, ep.assigned_date
            FROM EmployeeProject ep
            JOIN Employee e ON ep.employee_id = e.employee_id
            JOIN Project  p ON ep.project_id  = p.project_id
            ORDER BY p.project_name, e.name
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-22s %-25s %-12s%n", "Employee", "Project", "Assigned Date");
            System.out.println("-".repeat(60));
            while (rs.next()) {
                System.out.printf("%-22s %-25s %-12s%n",
                        rs.getString("employee"), rs.getString("project_name"),
                        rs.getDate("assigned_date"));
            }
        }
    }

    // 3. Project-wise Employee Count
    public void reportProjectWiseCount() throws SQLException {
        printHR("REPORT 3 – Project-wise Employee Count");
        String sql = """
            SELECT p.project_name, COUNT(ep.employee_id) AS employee_count
            FROM Project p
            LEFT JOIN EmployeeProject ep ON p.project_id = ep.project_id
            GROUP BY p.project_id, p.project_name
            ORDER BY employee_count DESC
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-25s %s%n", "Project", "Employee Count");
            System.out.println("-".repeat(40));
            while (rs.next())
                System.out.printf("%-25s %d%n",
                        rs.getString("project_name"), rs.getInt("employee_count"));
        }
    }

    // 4. Department-wise Salary Expense
    public void reportDeptSalaryExpense() throws SQLException {
        printHR("REPORT 4 – Department-wise Salary Expense");
        String sql = """
            SELECT d.department_name, SUM(e.salary) AS total_salary, COUNT(e.employee_id) AS headcount
            FROM Department d
            LEFT JOIN Employee e ON d.department_id = e.department_id
            GROUP BY d.department_id, d.department_name
            ORDER BY total_salary DESC
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-25s %15s %10s%n", "Department", "Total Salary", "Headcount");
            System.out.println("-".repeat(55));
            while (rs.next())
                System.out.printf("%-25s %15.2f %10d%n",
                        rs.getString("department_name"),
                        rs.getBigDecimal("total_salary"),
                        rs.getInt("headcount"));
        }
    }

    // 5. Highest Paid Employee
    public void reportHighestPaidEmployee() throws SQLException {
        printHR("REPORT 5 – Highest Paid Employee");
        String sql = """
            SELECT e.name, e.email, e.salary, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            ORDER BY e.salary DESC
            LIMIT 1
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                System.out.printf("Name: %-20s  Email: %-25s  Salary: %,.2f  Dept: %s%n",
                        rs.getString("name"), rs.getString("email"),
                        rs.getBigDecimal("salary"), rs.getString("department_name"));
        }
    }

    // 6. Top 3 Highest Paid Employees
    public void reportTop3HighestPaid() throws SQLException {
        printHR("REPORT 6 – Top 3 Highest Paid Employees");
        String sql = """
            SELECT e.name, e.email, e.salary, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            ORDER BY e.salary DESC
            LIMIT 3
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-5s %-20s %-25s %12s %-20s%n", "Rank", "Name", "Email", "Salary", "Department");
            System.out.println("-".repeat(85));
            int rank = 1;
            while (rs.next())
                System.out.printf("%-5d %-20s %-25s %12.2f %-20s%n",
                        rank++, rs.getString("name"), rs.getString("email"),
                        rs.getBigDecimal("salary"), rs.getString("department_name"));
        }
    }

    // 7. Employees Not Assigned to Any Project
    public void reportUnassignedEmployees() throws SQLException {
        printHR("REPORT 7 – Employees Not Assigned to Any Project");
        String sql = """
            SELECT e.employee_id, e.name, e.email, d.department_name
            FROM Employee e
            JOIN Department d ON e.department_id = d.department_id
            WHERE e.employee_id NOT IN (SELECT DISTINCT employee_id FROM EmployeeProject)
            ORDER BY e.name
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-5s %-20s %-25s %-20s%n", "ID", "Name", "Email", "Department");
            System.out.println("-".repeat(75));
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("%-5d %-20s %-25s %-20s%n",
                        rs.getInt("employee_id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("department_name"));
            }
            if (!any) System.out.println("  All employees are assigned to at least one project.");
        }
    }

    // 8. Projects Without Employees
    public void reportProjectsWithoutEmployees() throws SQLException {
        printHR("REPORT 8 – Projects Without Employees");
        String sql = """
            SELECT p.project_id, p.project_name, p.budget, p.start_date
            FROM Project p
            WHERE p.project_id NOT IN (SELECT DISTINCT project_id FROM EmployeeProject)
            ORDER BY p.project_name
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-5s %-25s %15s %-12s%n", "ID", "Project Name", "Budget", "Start Date");
            System.out.println("-".repeat(62));
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("%-5d %-25s %15.2f %-12s%n",
                        rs.getInt("project_id"), rs.getString("project_name"),
                        rs.getBigDecimal("budget"), rs.getDate("start_date"));
            }
            if (!any) System.out.println("  All projects have at least one employee assigned.");
        }
    }

    // 9. Average Salary by Department
    public void reportAvgSalaryByDept() throws SQLException {
        printHR("REPORT 9 – Average Salary by Department");
        String sql = """
            SELECT d.department_name, AVG(e.salary) AS avg_salary
            FROM Department d
            LEFT JOIN Employee e ON d.department_id = e.department_id
            GROUP BY d.department_id, d.department_name
            ORDER BY avg_salary DESC
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-25s %15s%n", "Department", "Avg Salary");
            System.out.println("-".repeat(42));
            while (rs.next())
                System.out.printf("%-25s %15.2f%n",
                        rs.getString("department_name"),
                        rs.getBigDecimal("avg_salary") != null ? rs.getBigDecimal("avg_salary") : java.math.BigDecimal.ZERO);
        }
    }

    // 10. Dashboard Summary
    public void reportDashboard() throws SQLException {
        printHR("REPORT 10 – Dashboard Summary");
        String sql = """
            SELECT
                (SELECT COUNT(*) FROM Employee)                 AS total_employees,
                (SELECT COUNT(*) FROM Department)               AS total_departments,
                (SELECT COUNT(*) FROM Project)                  AS total_projects,
                (SELECT COALESCE(SUM(salary),0) FROM Employee)  AS total_salary,
                (SELECT COALESCE(AVG(salary),0) FROM Employee)  AS avg_salary
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                System.out.printf("  Total Employees   : %d%n",  rs.getInt("total_employees"));
                System.out.printf("  Total Departments : %d%n",  rs.getInt("total_departments"));
                System.out.printf("  Total Projects    : %d%n",  rs.getInt("total_projects"));
                System.out.printf("  Total Salary Exp  : %,.2f%n", rs.getBigDecimal("total_salary"));
                System.out.printf("  Average Salary    : %,.2f%n", rs.getBigDecimal("avg_salary"));
            }
        }
    }
}
