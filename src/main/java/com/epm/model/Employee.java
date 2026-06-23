package com.epm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private int        employeeId;
    private String     name;
    private String     email;
    private BigDecimal salary;
    private LocalDate  joiningDate;
    private int        departmentId;
    private String     departmentName; // populated by JOIN queries

    public Employee() {}

    public int        getEmployeeId()     { return employeeId; }
    public String     getName()           { return name; }
    public String     getEmail()          { return email; }
    public BigDecimal getSalary()         { return salary; }
    public LocalDate  getJoiningDate()    { return joiningDate; }
    public int        getDepartmentId()   { return departmentId; }
    public String     getDepartmentName() { return departmentName; }

    public void setEmployeeId(int employeeId)           { this.employeeId     = employeeId; }
    public void setName(String name)                     { this.name           = name; }
    public void setEmail(String email)                   { this.email          = email; }
    public void setSalary(BigDecimal salary)             { this.salary         = salary; }
    public void setJoiningDate(LocalDate joiningDate)    { this.joiningDate    = joiningDate; }
    public void setDepartmentId(int departmentId)        { this.departmentId   = departmentId; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-25s | %12.2f | %-12s | %-20s |",
                employeeId, name, email, salary, joiningDate,
                departmentName != null ? departmentName : String.valueOf(departmentId));
    }
}
