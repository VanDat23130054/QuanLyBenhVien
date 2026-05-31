# Quản Lý Bệnh Viện (Hospital Management System)

A comprehensive Java-based hospital management application using SQL Server database.

## Features

- User Management and Role-Based Access Control
- Staff Schedule Management
- Attendance Tracking and Payroll
- Patient Management
- Medical Records
- Appointment Scheduling
- Pharmacy Management
- Billing and Financial Management

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- SQL Server 2019 or later
- SQL Server JDBC Driver 12.2.0

## Setup Instructions

### 1. Database Setup

1. Open SQL Server Management Studio
2. Run the database creation script:
   ```sql
   -- Execute the script from database/CreateDatabase.sql
   ```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Configure Database Connection

Edit `src/main/java/com/hospital/database/DatabaseConnection.java` and update:
- SERVER: Your SQL Server host
- DATABASE: Database name (default: QuanLyBenhVien)
- USERNAME: SQL Server username
- PASSWORD: SQL Server password
- PORT: SQL Server port (default: 1433)

Or update `src/main/resources/application.properties`

### 4. Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.hospital.App"
```

### 5. Run Tests

```bash
mvn test
```

## Project Structure

```
QuanLyBenhVien/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hospital/
│   │   │       ├── App.java
│   │   │       ├── database/
│   │   │       │   └── DatabaseConnection.java
│   │   │       ├── model/
│   │   │       ├── controller/
│   │   │       └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
└── database/
    └── CreateDatabase.sql
```

## Database Schema

The database includes the following main entities:
- **VaiTro** (Roles): Define user roles
- **NguoiDung** (Users): User accounts with authentication
- **LichTruc** (Schedules): Staff scheduling
- **ChamCong** (Attendance): Attendance records
- **TinhLuong** (Payroll): Salary management

## Technologies Used

- **Language**: Java 11
- **Build Tool**: Maven
- **Database**: SQL Server 2019+
- **JDBC Driver**: SQL Server JDBC 12.2.0
- **Testing**: JUnit 4

## Development

To add new features:

1. Create model classes in `com/hospital/model/`
2. Create service layer in `com/hospital/service/`
3. Create controllers in `com/hospital/controller/`
4. Write tests in `src/test/java/`

## License

This project is part of the HTTT curriculum.

## Contact

For questions or issues, please contact the development team.
