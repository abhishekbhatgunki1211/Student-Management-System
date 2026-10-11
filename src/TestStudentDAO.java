
import dao.StudentDAO;
import model.Student;

public class TestStudentDAO {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // Test 1: Add a student
        Student student = new Student(
            1001,
            "Rahul Patil",
            "rahul.test@example.com",
            "AI & Data Science",
            85.5
        );

        boolean added = dao.addStudent(student);
        System.out.println("Add test: " + (added ? "PASS" : "FAIL"));

        // Test 2: Search for the student
        Student found = dao.getStudentById(1001);
        System.out.println("Search test: "
            + (found != null ? "PASS" : "FAIL"));

        // Test 3: Update the student
        if (found != null) {
            found.setMarks(91.0);
            boolean updated = dao.updateStudent(found);
            System.out.println("Update test: "
                + (updated ? "PASS" : "FAIL"));
        }

        // Test 4: Delete the student
        boolean deleted = dao.deleteStudent(1001);
        System.out.println("Delete test: "
            + (deleted ? "PASS" : "FAIL"));

        // Test 5: Confirm deletion
        Student deletedStudent = dao.getStudentById(1001);
        System.out.println("Deletion verification: "
            + (deletedStudent == null ? "PASS" : "FAIL"));
    }
}
