package assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class LecturerController {

    @FXML
    private TableView<Student> attendanceTable;

    @FXML
    private TableColumn<Student, String> studentNameCol;
    @FXML
    private TableColumn<Student, Integer> studentNumberCol;
    @FXML
    private TableColumn<Student, Boolean> mondayAttendanceCol;
    @FXML
    private TableColumn<Student, Boolean> tuesdayAttendanceCol;
    @FXML
    private TableColumn<Student, Boolean> wednesdayAttendanceCol;

    @FXML
    private TextArea challengesTextArea;
    @FXML
    private TextArea chapterDetailsTextArea;
    @FXML
    private Button submitButton;

    private Connection connection;

    public LecturerController() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/academic", "root", "");
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    @FXML
    private void initialize() {
        configureStudentColumns();
        populateTableWithStudents();
    }

    private void configureStudentColumns() {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentNumberCol.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        setupAttendanceCheckBoxColumn(mondayAttendanceCol, "mondayPresent");
        setupAttendanceCheckBoxColumn(tuesdayAttendanceCol, "tuesdayPresent");
        setupAttendanceCheckBoxColumn(wednesdayAttendanceCol, "wednesdayPresent");
    }

    private void setupAttendanceCheckBoxColumn(TableColumn<Student, Boolean> column, String propertyName) {
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column));  // Use forTableColumn method
        column.setEditable(true);
    }


    private void populateTableWithStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "SELECT student_id, name, student_number FROM students";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                int number = rs.getInt("student_number");

                // Create a student object and add it to the list
                Student student = new Student(name, number, false, false, false);
                student.setStudentId(id);
                students.add(student);
            }

            attendanceTable.setItems(students);
            attendanceTable.setEditable(true);

        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    @FXML
    private void submitReport() {
        String challenges = challengesTextArea.getText();
        String chapterDetails = chapterDetailsTextArea.getText();
        String employeeNumber = getCurrentLecturerEmployeeNumber();

        if (challenges.isEmpty() || chapterDetails.isEmpty()) {
            showAlert("Please fill in all fields before submitting.");
            return;
        }

        // Check if the report already exists for the current lecturer
        if (isReportDuplicate(employeeNumber, challenges, chapterDetails)) {
            showAlert("The report with the same challenges and chapter details already exists.");
            return;
        }

        boolean attendanceSuccess = recordAttendance(); // Call to record attendance
        if (attendanceSuccess) {
            try {
                saveReport(employeeNumber, challenges, chapterDetails);
                showAlert("Report submitted successfully!");
            } catch (SQLException e) {
                logSQLException(e);
                showAlert("Error submitting report.");
            }
        } else {
            showAlert("Error recording attendance. Report not submitted.");
        }
    }

    private boolean recordAttendance() {
        try {
            connection.setAutoCommit(false); // Start transaction

            for (Student student : attendanceTable.getItems()) {
                if (student.isMondayPresent() || student.isTuesdayPresent() || student.isWednesdayPresent()) {
                    // Only save attendance if the student is marked present on any day
                    saveAttendance(student);
                }
            }

            connection.commit(); // Commit transaction if all updates succeed
            System.out.println("Attendance recorded successfully.");
            return true; // Attendance recorded successfully

        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback if an error occurs
                System.out.println("Attendance recording failed, rolling back changes.");
            } catch (SQLException rollbackEx) {
                logSQLException(rollbackEx);
            }
            logSQLException(e);
            return false; // Attendance recording failed

        } finally {
            try {
                connection.setAutoCommit(true); // Restore default behavior
            } catch (SQLException ex) {
                logSQLException(ex);
            }
        }
    }

    private void saveAttendance(Student student) throws SQLException {
        String query = "INSERT INTO attendance (student_id, attendance_date, monday_present, tuesday_present, wednesday_present) VALUES (?, CURRENT_DATE, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getStudentId());
            stmt.setBoolean(2, student.isMondayPresent());
            stmt.setBoolean(3, student.isTuesdayPresent());
            stmt.setBoolean(4, student.isWednesdayPresent());
            int rowsAffected = stmt.executeUpdate(); // Execute the insertion

            if (rowsAffected > 0) {
                System.out.println("Attendance recorded for Student ID: " + student.getStudentId());
            } else {
                System.out.println("Failed to record attendance for Student ID: " + student.getStudentId());
            }
        } catch (SQLException e) {
            logSQLException(e);
            throw e; // Re-throw to handle it in the calling method
        }
    }

    private void saveReport(String employeeNumber, String challenges, String chapterDetails) throws SQLException {
        String query = "INSERT INTO reports (employee_number, report_date, challenges, chapter_details) VALUES (?, CURRENT_DATE, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeNumber);
            stmt.setString(2, challenges);
            stmt.setString(3, chapterDetails);
            stmt.executeUpdate();
            System.out.println("Report saved to database.");
        } catch (SQLException e) {
            logSQLException(e);
            throw e; // Re-throw to handle it in the calling method
        }
    }

    private boolean isReportDuplicate(String employeeNumber, String challenges, String chapterDetails) {
        String query = "SELECT COUNT(*) FROM reports WHERE employee_number = ? AND challenges = ? AND chapter_details = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeNumber);
            stmt.setString(2, challenges);
            stmt.setString(3, chapterDetails);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count is greater than 0, a duplicate exists
            }
        } catch (SQLException e) {
            logSQLException(e);
        }

        return false; // No duplicate found
    }

    private String getCurrentLecturerEmployeeNumber() {
        return "202410002"; // Placeholder for actual employee number retrieval
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void logSQLException(SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        e.printStackTrace(); // Print stack trace for debugging
    }

    public void addAttendance(ActionEvent actionEvent) {
    }

    public static class Student {
        private String name;
        private int studentNumber;
        private boolean mondayPresent;
        private boolean tuesdayPresent;
        private boolean wednesdayPresent;
        private int studentId;

        public Student(String name, int studentNumber, boolean mondayPresent, boolean tuesdayPresent, boolean wednesdayPresent) {
            this.name = name;
            this.studentNumber = studentNumber;
            this.mondayPresent = mondayPresent;
            this.tuesdayPresent = tuesdayPresent;
            this.wednesdayPresent = wednesdayPresent;
        }

        public String getName() { return name; }
        public int getStudentNumber() { return studentNumber; }
        public boolean isMondayPresent() { return mondayPresent; }
        public void setMondayPresent(boolean mondayPresent) { this.mondayPresent = mondayPresent; }
        public boolean isTuesdayPresent() { return tuesdayPresent; }
        public void setTuesdayPresent(boolean tuesdayPresent) { this.tuesdayPresent = tuesdayPresent; }
        public boolean isWednesdayPresent() { return wednesdayPresent; }
        public void setWednesdayPresent(boolean wednesdayPresent) { this.wednesdayPresent = wednesdayPresent; }
        public int getStudentId() { return studentId; }
        public void setStudentId(int studentId) { this.studentId = studentId; }
    }
}
