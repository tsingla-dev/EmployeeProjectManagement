# Employee Project Management System

A console-based Java + JDBC + MySQL application for managing employees, departments, projects, and project assignments.

---

## Project Overview

This system allows an organisation to:
- Manage **Departments** and **Employees** (with One-to-One address records)
- Manage **Projects** and assign/unassign employees to them (Many-to-Many)
- Generate **10 analytical reports** using SQL joins and aggregation

---

## Database Design

### Entities & Relationships

| Table            | Primary Key  | Relationship                         |
|------------------|-------------|--------------------------------------|
| Department       | department_id | Referenced by Employee               |
| Employee         | employee_id  | Belongs to one Department            |
| EmployeeAddress  | address_id   | One-to-One with Employee             |
| Project          | project_id   | Many-to-Many with Employee           |
| EmployeeProject  | (employee_id, project_id) | Junction/mapping table  |

### Normalization
- **1NF** – All columns are atomic; no repeating groups.
- **2NF** – All non-key attributes depend on the full primary key (junction table uses composite PK).
- **3NF** – No transitive dependencies; department name lives only in the `Department` table.

### Key Constraints
- `Employee.department_id` → FK to `Department` (ON DELETE RESTRICT)
- `EmployeeAddress.employee_id` → FK to `Employee` (ON DELETE CASCADE, UNIQUE = One-to-One)
- `EmployeeProject.(employee_id, project_id)` → composite PK + FK to both parent tables

---

## Setup Instructions

### Prerequisites
| Tool | Version |
|------|---------|
| Java JDK | 17 or later |
| MySQL Community Server | 8.x |
| MySQL Connector/J | 8.3.0 |
| VS Code | Latest |
| Git | Latest |

---

### Step-by-Step Setup (Intel Mac)

#### 1. Install Java
```bash
# Check if already installed
java -version

# If not, install via Homebrew
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

#### 2. Install MySQL from brew or from straight up google chrome
```bash
brew install mysql
brew services start mysql
mysql_secure_installation   
```

#### 3. Create the Database
```bash
mysql -u root -p

```

```bash
mysql -u root -p < schema.sql
```

#### 4. Download MySQL Connector/J
1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Select **Platform Independent → ZIP Archive**
3. Unzip and copy `mysql-connector-j-8.3.0.jar` into the project's `lib/` folder

#### 5. Configure Database Password
Open `src/main/java/com/epm/util/DBConnection.java` and set:
```java
private static final String PASSWORD = "Tanvi@2006"; 
```

#### 6. Compile & Run
```bash
chmod +x run.sh
./run.sh
```
Or use **VS Code** → Terminal → Run Task → **Run EPM**

---

## Package Structure

```
src/main/java/com/epm/
├── model/          # Plain Java objects (Department, Employee, etc.)
├── dao/            # JDBC data access (PreparedStatement queries)
├── service/        # Business logic layer
├── ui/             # Console menus (one per module)
├── reports/        # All 10 SQL reports
└── util/           # DBConnection, InputHelper
```

---

## Assumptions

1. MySQL root password is `Tanvi@2006` .
2. Dates are entered in `yyyy-MM-dd` format.
3. Deleting a Department fails if employees still belong to it (referential integrity).
4. Deleting an Employee cascades to delete their address and project assignments.
5. Employee-Project assignment uses a database transaction that validates both IDs before committing.


---

## Git Repository Setup (for submission)

See the detailed Git instructions in the setup guide provided separately.
