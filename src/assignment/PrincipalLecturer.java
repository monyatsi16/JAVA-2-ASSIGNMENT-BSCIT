package assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PrincipalLecturer {

    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private ComboBox<String> moduleComboBox;
    @FXML
    private DatePicker reportDatePicker;
    @FXML
    private TextArea challengesArea;
    @FXML
    private TextArea recommendationsArea;
    @FXML
    private Button submitButton;

    private Connection connection;
    private int lecturerId = 1; // Example lecturer ID, replace as needed for logged-in lecturer

    public PrincipalLecturer() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/academic", "root", "");
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    @FXML
    private void initialize() {
        loadClasses();
        loadModules();
        clearFormFields();  // Clear form fields on load
    }

    private void loadClasses() {
        String query = "SELECT class_name FROM classes";
        ObservableList<String> classes = FXCollections.observableArrayList();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                classes.add(rs.getString("class_name"));
            }
            classComboBox.setItems(classes);

        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    private void loadModules() {
        String query = "SELECT module_name FROM modules";
        ObservableList<String> modules = FXCollections.observableArrayList();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                modules.add(rs.getString("module_name"));
            }
            moduleComboBox.setItems(modules);

        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    @FXML
    private void submitReport() {
        String selectedClass = classComboBox.getValue();
        String selectedModule = moduleComboBox.getValue();
        LocalDate reportDate = reportDatePicker.getValue();
        String challenges = challengesArea.getText();
        String recommendations = recommendationsArea.getText();
        String employeeNumber = getCurrentLecturerEmployeeNumber();

        if (selectedClass == null || selectedModule == null || reportDate == null || challenges.isEmpty() || recommendations.isEmpty()) {
            showAlert("Please fill in all fields before submitting.");
            return;
        }

        // Check if a report already exists within the same week for the lecturer
        if (isReportDuplicate(employeeNumber, selectedClass, selectedModule, reportDate)) {
            showAlert("A report has already been submitted within the past week for this class and module.");
            return;
        }

        try {
            saveReport(employeeNumber, selectedClass, selectedModule, reportDate, challenges, recommendations);
            showAlert("Report submitted successfully!");
            clearFormFields();  // Clear the form fields after successful submission
        } catch (SQLException e) {
            logSQLException(e);
            showAlert("Error submitting report.");
        }
    }

    private void saveReport(String employeeNumber, String selectedClass, String selectedModule, LocalDate reportDate, String challenges, String recommendations) throws SQLException {
        String query = "INSERT INTO reports (employee_number, class_name, module_name, report_date, challenges, recommendations) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeNumber);
            stmt.setString(2, selectedClass);
            stmt.setString(3, selectedModule);
            stmt.setDate(4, Date.valueOf(reportDate));
            stmt.setString(5, challenges);
            stmt.setString(6, recommendations);
            stmt.executeUpdate();
            System.out.println("Report saved to database.");
        } catch (SQLException e) {
            logSQLException(e);
            throw e; // Re-throw to handle it in the calling method
        }
    }

    private boolean isReportDuplicate(String employeeNumber, String selectedClass, String selectedModule, LocalDate reportDate) {
        String query = "SELECT report_date FROM reports WHERE employee_number = ? AND class_name = ? AND module_name = ? ORDER BY report_date DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeNumber);
            stmt.setString(2, selectedClass);
            stmt.setString(3, selectedModule);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDate lastReportDate = rs.getDate("report_date").toLocalDate();
                long daysDifference = ChronoUnit.DAYS.between(lastReportDate, reportDate);
                return daysDifference < 7; // Duplicate if less than 7 days since the last report
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

    private void clearFormFields() {
        // Clear all form fields
        classComboBox.getSelectionModel().clearSelection();
        moduleComboBox.getSelectionModel().clearSelection();
        reportDatePicker.setValue(LocalDate.now());
        challengesArea.clear();
        recommendationsArea.clear();
    }

    public void addAttendance(ActionEvent actionEvent) {
        // Placeholder for additional functionality
    }
}
