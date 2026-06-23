package com.epm.ui;

import com.epm.model.EmployeeAddress;
import com.epm.service.AddressService;
import com.epm.util.InputHelper;

import java.sql.SQLException;

public class AddressMenu {
    private final AddressService svc = new AddressService();

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── Address Management ─────────────────────");
            System.out.println("  1. Add Address for Employee");
            System.out.println("  2. View Employee Address");
            System.out.println("  3. Update Address");
            System.out.println("  0. Back");
            System.out.println("────────────────────────────────────────────");
            int choice = InputHelper.readInt("  Choose: ");
            try {
                switch (choice) {
                    case 1 -> addAddr();
                    case 2 -> viewAddr();
                    case 3 -> updateAddr();
                    case 0 -> back = true;
                    default -> System.out.println("  Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("  ❌ DB Error: " + e.getMessage());
            }
        }
    }

    private void addAddr() throws SQLException {
        int empId     = InputHelper.readInt("  Employee ID: ");
        String city   = InputHelper.readString("  City: ");
        String state  = InputHelper.readString("  State: ");
        String pin    = InputHelper.readString("  Pincode: ");
        EmployeeAddress a = svc.addAddress(empId, city, state, pin);
        System.out.println("  ✅ Address added – Address ID: " + a.getAddressId());
    }

    private void viewAddr() throws SQLException {
        int empId = InputHelper.readInt("  Employee ID: ");
        svc.getByEmployee(empId).ifPresentOrElse(a -> {
            System.out.printf("%n  Address ID : %d%n", a.getAddressId());
            System.out.printf("  City       : %s%n", a.getCity());
            System.out.printf("  State      : %s%n", a.getState());
            System.out.printf("  Pincode    : %s%n", a.getPincode());
        }, () -> System.out.println("  ⚠ No address found for this employee."));
    }

    private void updateAddr() throws SQLException {
        int empId = InputHelper.readInt("  Employee ID: ");
        svc.getByEmployee(empId).ifPresentOrElse(a -> {
            try {
                String city  = InputHelper.readString("  City  [" + a.getCity()   + "]: ");
                String state = InputHelper.readString("  State [" + a.getState()  + "]: ");
                String pin   = InputHelper.readString("  Pin   [" + a.getPincode()+ "]: ");
                boolean ok = svc.updateAddress(empId,
                        city.isEmpty()  ? a.getCity()    : city,
                        state.isEmpty() ? a.getState()   : state,
                        pin.isEmpty()   ? a.getPincode() : pin);
                System.out.println(ok ? "  ✅ Updated." : "  ⚠ No change.");
            } catch (SQLException e) { System.out.println("  ❌ " + e.getMessage()); }
        }, () -> System.out.println("  ⚠ No address found. Add one first."));
    }
}
