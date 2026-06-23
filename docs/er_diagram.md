# ER Diagram – Employee Project Management System

```
┌─────────────────────┐         ┌──────────────────────────────────┐
│     Department      │         │            Employee              │
├─────────────────────┤         ├──────────────────────────────────┤
│ PK department_id    │◄───────┤ PK employee_id                   │
│    department_name  │  N:1   │    name                          │
└─────────────────────┘         │    email (UNIQUE)                │
                                 │    salary                        │
                                 │    joining_date                  │
                                 │ FK department_id                 │
                                 └──────────────┬───────────────────┘
                                                │
                           ┌────────────────────┤1
                           │                    │
                         1 ▼                    │ N
               ┌───────────────────┐   ┌────────────────────────┐
               │  EmployeeAddress  │   │   EmployeeProject      │
               ├───────────────────┤   ├────────────────────────┤
               │ PK address_id     │   │ PK,FK employee_id      │
               │ FK employee_id    │   │ PK,FK project_id       │
               │    city           │   │    assigned_date        │
               │    state          │   └────────────┬───────────┘
               │    pincode        │                │ N
               └───────────────────┘                │
                                                     │ 1
                                          ┌──────────▼──────────┐
                                          │       Project        │
                                          ├──────────────────────┤
                                          │ PK project_id        │
                                          │    project_name       │
                                          │    budget            │
                                          │    start_date        │
                                          └──────────────────────┘
```

## Relationships Summary

| Relationship | Type | Description |
|---|---|---|
| Department → Employee | One-to-Many | One dept has many employees |
| Employee → EmployeeAddress | One-to-One | Each employee has one address |
| Employee ↔ Project | Many-to-Many | Via EmployeeProject junction table |
