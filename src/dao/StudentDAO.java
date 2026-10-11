
package dao;

import model.Student;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Add a student
    public boolean addStudent(Student student) {
        String sql = """
            INSERT INTO students (id, name, email, department, marks)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getDepartment());
            statement.setDouble(5, student.getMarks());

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Could not add student: " + e.getMessage());
            return false;
        }
    }

    // Display all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                students.add(mapStudent(result));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve students: " + e.getMessage());
        }

        return students;
    }

    // Search by ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapStudent(result);
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not search student: " + e.getMessage());
        }

        return null;
    }

    // Update student
    public boolean updateStudent(Student student) {
        String sql = """
            UPDATE students
            SET name = ?, email = ?, department = ?, marks = ?
            WHERE id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getDepartment());
            statement.setDouble(4, student.getMarks());
            statement.setInt(5, student.getId());

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Could not update student: " + e.getMessage());
            return false;
        }
    }

    // Delete student
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Could not delete student: " + e.getMessage());
            return false;
        }
    }

    // Convert a database row into a Student object
    private Student mapStudent(ResultSet result) throws SQLException {
        return new Student(
            result.getInt("id"),
            result.getString("name"),
            result.getString("email"),
            result.getString("department"),
            result.getDouble("marks")
        );
    }
}
