# Student Management System

A console-based Student Management System developed using Java, Object-Oriented Programming (OOP), JDBC, and SQLite.

## Features

- Add new student records
- View all students
- Search students by ID
- Update student details
- Delete student records with confirmation
- Validate student information
- Store records persistently in an SQLite database
- Use prepared statements for database operations

## Technologies Used

- Java 21
- JDBC
- SQLite
- SQLite JDBC Driver
- VS Code
- Git and GitHub

## Project Structure

```text
StudentManagementSystem/
├── src/
│   ├── Main.java
│   ├── dao/
│   │   └── StudentDAO.java
│   ├── database/
│   │   ├── DatabaseConnection.java
│   │   └── DatabaseSetup.java
│   └── model/
│       └── Student.java
├── lib/
│   └── sqlite-jdbc-3.53.4.0.jar
├── .gitignore
└── README.md
```

## Prerequisites

- Java JDK 21 or compatible version
- SQLite JDBC driver included in the `lib` folder
- PowerShell or a terminal

## How to Run

Open PowerShell in the project directory.

### 1. Compile the project

```powershell
javac -cp ".\lib\sqlite-jdbc-3.53.4.0.jar" -d .\out .\src\database\DatabaseConnection.java .\src\database\DatabaseSetup.java .\src\model\Student.java .\src\dao\StudentDAO.java .\src\Main.java
```

### 2. Run the application

```powershell
java -cp ".\lib\sqlite-jdbc-3.53.4.0.jar;.\out" Main
```

The application creates the SQLite database table automatically when it starts.

## Learning Outcomes

- Practiced Java classes, objects, and encapsulation
- Implemented CRUD operations
- Connected Java to SQLite using JDBC
- Used prepared statements and resource management
- Organized code into model, DAO, and database packages

## Future Improvements

- Add a graphical user interface
- Implement student sorting and filtering
- Add unit tests
- Export student records to CSV
- Improve input validation and error handling

## Author

Developed as a Java learning and placement preparation project.
