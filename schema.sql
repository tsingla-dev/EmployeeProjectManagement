-- ============================================================
-- Employee Project Management System - Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS emp_project_db;
USE emp_project_db;

-- ----------------------------
-- Table: Department
-- ----------------------------
CREATE TABLE IF NOT EXISTS Department (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

-- ----------------------------
-- Table: Employee
-- ----------------------------
CREATE TABLE IF NOT EXISTS Employee (
    employee_id     INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    salary          DECIMAL(12,2) NOT NULL,
    joining_date    DATE NOT NULL,
    department_id   INT NOT NULL,
    CONSTRAINT fk_emp_dept FOREIGN KEY (department_id)
        REFERENCES Department(department_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ----------------------------
-- Table: EmployeeAddress (One-to-One with Employee)
-- ----------------------------
CREATE TABLE IF NOT EXISTS EmployeeAddress (
    address_id   INT AUTO_INCREMENT PRIMARY KEY,
    employee_id  INT NOT NULL UNIQUE,
    city         VARCHAR(100) NOT NULL,
    state        VARCHAR(100) NOT NULL,
    pincode      VARCHAR(10)  NOT NULL,
    CONSTRAINT fk_addr_emp FOREIGN KEY (employee_id)
        REFERENCES Employee(employee_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table: Project
-- ----------------------------
CREATE TABLE IF NOT EXISTS Project (
    project_id   INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(150) NOT NULL UNIQUE,
    budget       DECIMAL(15,2) NOT NULL,
    start_date   DATE NOT NULL
);

-- ----------------------------
-- Table: EmployeeProject (Many-to-Many mapping)
-- ----------------------------
CREATE TABLE IF NOT EXISTS EmployeeProject (
    employee_id   INT NOT NULL,
    project_id    INT NOT NULL,
    assigned_date DATE NOT NULL,
    PRIMARY KEY (employee_id, project_id),
    CONSTRAINT fk_ep_emp FOREIGN KEY (employee_id)
        REFERENCES Employee(employee_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ep_proj FOREIGN KEY (project_id)
        REFERENCES Project(project_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Sample seed data (optional – remove if not needed)
-- ----------------------------
INSERT IGNORE INTO Department (department_name) VALUES
    ('Engineering'), ('Human Resources'), ('Finance'), ('Marketing');

INSERT IGNORE INTO Employee (name, email, salary, joining_date, department_id) VALUES
    ('Alice Johnson',  'alice@epm.com',  95000.00, '2021-03-15', 1),
    ('Bob Smith',      'bob@epm.com',    72000.00, '2020-07-01', 2),
    ('Carol White',    'carol@epm.com',  88000.00, '2019-11-20', 1),
    ('David Brown',    'david@epm.com',  61000.00, '2022-01-10', 3),
    ('Eva Green',      'eva@epm.com',    54000.00, '2023-05-05', 4);

INSERT IGNORE INTO EmployeeAddress (employee_id, city, state, pincode) VALUES
    (1, 'San Francisco', 'California',  '94105'),
    (2, 'Austin',        'Texas',       '73301'),
    (3, 'New York',      'New York',    '10001');

INSERT IGNORE INTO Project (project_name, budget, start_date) VALUES
    ('Apollo CRM',      500000.00, '2023-01-01'),
    ('Hermes Analytics',320000.00, '2023-06-15'),
    ('Zeus ERP',        750000.00, '2022-09-01');

INSERT IGNORE INTO EmployeeProject (employee_id, project_id, assigned_date) VALUES
    (1, 1, '2023-01-10'),
    (1, 3, '2022-09-05'),
    (3, 1, '2023-02-01'),
    (3, 2, '2023-07-01'),
    (4, 3, '2022-10-01');
