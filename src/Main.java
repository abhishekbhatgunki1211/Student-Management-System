import dao.StudentDAO;
import database.DatabaseSetup;
import model.Student;
import java.util.Scanner;

public class Main {

    private static boolean isValidEmail(String email) {
        return email != null &&
            email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            System.out.println("Please enter a whole number.");
            scanner.nextLine();
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            }
            System.out.println("Please enter a numeric value.");
            scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        DatabaseSetup.createTable();
        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");

            choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    int id = readInt(scanner, "Enter student ID: ");
                    if (id <= 0) {
                        System.out.println("Student ID must be positive.");
                        break;
                    }

                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine().trim();

                    System.out.print("Enter department: ");
                    String department = scanner.nextLine().trim();

                    double marks = readDouble(scanner, "Enter marks (0-100): ");

                    if (name.isBlank() || email.isBlank() || department.isBlank()) {
                        System.out.println("Fields cannot be empty.");
                    } else if (!isValidEmail(email)) {
                        System.out.println("Invalid email format.");
                    } else if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
                        System.out.println("Marks must be between 0 and 100.");
                    } else {
                        Student student = new Student(id, name, email, department, marks);
                        if (dao.addStudent(student)) {
                            System.out.println("Student added successfully!");
                        } else {
                            System.out.println("Could not add student. Check duplicate ID/email.");
                        }
                    }
                }
                case 2 -> {
                    var students = dao.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.printf("%-8s %-20s %-30s %-22s %-8s%n",
                                "ID", "Name", "Email", "Department", "Marks");
                        System.out.println("-".repeat(92));
                        for (Student s : students) {
                            System.out.printf("%-8d %-20s %-30s %-22s %-8.2f%n",
                                    s.getId(), s.getName(), s.getEmail(),
                                    s.getDepartment(), s.getMarks());
                        }
                    }
                }
                case 3 -> {
                    int id = readInt(scanner, "Enter student ID to search: ");
                    Student s = dao.getStudentById(id);
                    if (s == null) {
                        System.out.println("Student not found.");
                    } else {
                        System.out.println("ID: " + s.getId());
                        System.out.println("Name: " + s.getName());
                        System.out.println("Email: " + s.getEmail());
                        System.out.println("Department: " + s.getDepartment());
                        System.out.printf("Marks: %.2f%n", s.getMarks());
                    }
                }
                case 4 -> {
                    int id = readInt(scanner, "Enter student ID to update: ");
                    Student s = dao.getStudentById(id);
                    if (s == null) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Enter new department: ");
                    String department = scanner.nextLine().trim();
                    double marks = readDouble(scanner, "Enter new marks (0-100): ");

                    if (name.isBlank() || email.isBlank() || department.isBlank()) {
                        System.out.println("Fields cannot be empty.");
                    } else if (!isValidEmail(email)) {
                        System.out.println("Invalid email format.");
                    } else if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
                        System.out.println("Marks must be between 0 and 100.");
                    } else {
                        s.setName(name);
                        s.setEmail(email);
                        s.setDepartment(department);
                        s.setMarks(marks);
                        System.out.println(dao.updateStudent(s)
                                ? "Student updated successfully!"
                                : "Update failed. Check whether the email is already used.");
                    }
                }
                case 5 -> {
                    int id = readInt(scanner, "Enter student ID to delete: ");
                    Student s = dao.getStudentById(id);
                    if (s == null) {
                        System.out.println("Student not found.");
                    } else {
                        System.out.println("Student: " + s.getName());
                        System.out.print("Confirm deletion (yes/no): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                            System.out.println(dao.deleteStudent(id)
                                    ? "Student deleted successfully!"
                                    : "Could not delete student.");
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                    }
                }
                case 0 -> System.out.println("Exiting application. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
