package com.epm.ui;

import com.epm.util.InputHelper;

public class MainMenu {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Employee Project Management System  v1.0   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        DepartmentMenu deptMenu   = new DepartmentMenu();
        EmployeeMenu   empMenu    = new EmployeeMenu();
        AddressMenu    addrMenu   = new AddressMenu();
        ProjectMenu    projMenu   = new ProjectMenu();
        AssignmentMenu assignMenu = new AssignmentMenu();
        ReportsMenu    rptMenu    = new ReportsMenu();

        boolean running = true;
        while (running) {
            System.out.println("\n══════════════ MAIN MENU ══════════════════");
            System.out.println("  1. Department Management");
            System.out.println("  2. Employee Management");
            System.out.println("  3. Address Management");
            System.out.println("  4. Project Management");
            System.out.println("  5. Employee-Project Assignments");
            System.out.println("  6. Reports");
            System.out.println("  0. Exit");
            System.out.println("════════════════════════════════════════════");
            int choice = InputHelper.readInt("  Choose: ");
            switch (choice) {
                case 1 -> deptMenu.show();
                case 2 -> empMenu.show();
                case 3 -> addrMenu.show();
                case 4 -> projMenu.show();
                case 5 -> assignMenu.show();
                case 6 -> rptMenu.show();
                case 0 -> { running = false; System.out.println("\n  Goodbye! 👋"); }
                default -> System.out.println("  Invalid option.");
            }
        }
    }
}
