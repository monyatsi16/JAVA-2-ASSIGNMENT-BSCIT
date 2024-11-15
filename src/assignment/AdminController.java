package assignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Button; // Import Button if needed




public class AdminController {

    // Add UI TextFields for Admin Profile
    @FXML
    private TextField adminNameField; // UI TextField for Admin Name
    @FXML
    private TextField adminEmployeeNumberField; // UI TextField for Employee Number
    @FXML
    private TextField adminRoleField; // UI TextField for Role
    private Connection connection;

    @FXML
    private TextField lecturerNameField; // UI TextField for Lecturer Name
    @FXML
    private TextField lecturerEmployeeNumberField; // UI TextField for Employee Number
    @FXML
    private TextField lecturerDepartmentField; // UI TextField for Department
    @FXML
    private TextField academicYearField; // UI TextField for Academic Year
    @FXML
    private TextField semesterNameField; // UI TextField for Semester Name
    @FXML
    private TextField moduleNameField; // UI TextField for Module Name
    @FXML
    private TextField moduleCodeField; // UI TextField for Module Code
    @FXML
    private TextField classNameField; // UI TextField for Class Name
    @FXML
    private TextField studentNameField; // UI TextField for Student Name
    @FXML
    private TextField studentNumberField; // UI TextField for Student Number
    @FXML
    private TextField studentClassIdField; // UI TextField for Class ID
    @FXML
    private ComboBox<String> lecturerComboBox; // ComboBox for selecting a lecturer
    @FXML
    private ComboBox<String> moduleComboBox; // ComboBox for selecting a module
    @FXML
    private ComboBox<String> classComboBox; // ComboBox for selecting a class
    @FXML
    private ComboBox<String> studentComboBox;
    @FXML
    private ComboBox<String> assignModuleComboBox;

    // No-argument constructor
    public AdminController() {
        // Initialize your connection here or in an initialize method
    }

    @FXML
    public void initialize() {
        // Establish your database connection here
        try {
            // Replace these values with your actual database credentials
            String url = "jdbc:mysql://localhost:3306/academic";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
            // Populate ComboBoxes
            populateLecturerComboBox();
            populateModuleComboBox();
            populateClassComboBox();
            populateassignModuleComboBox();
            populateStudentComboBox();
        } catch (Exception e) {
            showAlert("Error", "Failed to connect to the database: " + e.getMessage());
        }
    }



    // Show an alert dialog for messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 2. Add New Lecturer
    public void addLecturer(String name, String employeeNumber, String department) {
        try {
            String sql = "INSERT INTO lecturers (name, employee_number, department) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, employeeNumber);
            stmt.setString(3, department);
            stmt.executeUpdate();

            // Add to users table for login
            addUser(employeeNumber, employeeNumber, "LECTURER", employeeNumber);
            showAlert("Success", "Lecturer added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add lecturer: " + e.getMessage());
        }
    }

    // Utility function to add user (for both Admin and Lecturer)
    private void addUser(String username, String password, String role, String employeeNumber) {
        try {
            String sql = "INSERT INTO users (username, password, role, employee_number) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, employeeNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            showAlert("Error", "Failed to add user: " + e.getMessage());
        }
    }

    // 3. Handle Add Lecturer Button Click
    @FXML
    public void handleAddLecturer(ActionEvent event) {
        String name = lecturerNameField.getText();
        String employeeNumber = lecturerEmployeeNumberField.getText();
        String department = lecturerDepartmentField.getText();

        // Debugging: print values to console
        System.out.println("Name: " + name);
        System.out.println("Employee Number: " + employeeNumber);
        System.out.println("Department: " + department);

        if (name.isEmpty() || employeeNumber.isEmpty() || department.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        addLecturer(name, employeeNumber, department);
    }

    // 4. Add New Academic Year
    public void addAcademicYear(String year) {
        try {
            String sql = "INSERT INTO academic_years (year) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, year);
            stmt.executeUpdate();
            showAlert("Success", "Academic year added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add academic year: " + e.getMessage());
        }
    }

    @FXML
    public void handleAddAcademicYear(ActionEvent event) {
        String year = academicYearField.getText();
        if (year.isEmpty()) {
            showAlert("Input Error", "Please enter an academic year.");
            return;
        }
        addAcademicYear(year);
    }

    // 5. Add New Semester
    @FXML
    public void handleAddSemester(ActionEvent event) {
        String semesterName = semesterNameField.getText();
        if (semesterName.isEmpty()) {
            showAlert("Input Error", "Please enter a semester name.");
            return;
        }
        addSemester(semesterName);
    }

    public void addSemester(String semesterName) {
        try {
            String sql = "INSERT INTO semesters (semester_name) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, semesterName);
            stmt.executeUpdate();
            showAlert("Success", "Semester added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add semester: " + e.getMessage());
        }
    }

    // 6. Add New Module
    @FXML
    public void handleAddModule(ActionEvent event) {
        String moduleName = moduleNameField.getText();
        String code = moduleCodeField.getText();
        if (moduleName.isEmpty() || code.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }
        addModule(moduleName, code);
    }

    public void addModule(String moduleName, String code) {
        try {
            String sql = "INSERT INTO modules (module_name, code) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, moduleName);
            stmt.setString(2, code);
            stmt.executeUpdate();
            showAlert("Success", "Module added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add module: " + e.getMessage());
        }
    }

    // 7. Add New Class
    @FXML
    public void handleAddClass(ActionEvent event) {
        String className = classNameField.getText();
        if (className.isEmpty()) {
            showAlert("Input Error", "Please enter a class name.");
            return;
        }
        addClass(className);
    }

    public void addClass(String className) {
        try {
            String sql = "INSERT INTO classes (class_name) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, className);
            stmt.executeUpdate();
            showAlert("Success", "Class added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add class: " + e.getMessage());
        }
    }

    // 10. Add New Student
    @FXML
    public void handleAddStudent(ActionEvent event) {
        String name = studentNameField.getText();
        String studentNumber = studentNumberField.getText();
        String classId = studentClassIdField.getText();
        if (name.isEmpty() || studentNumber.isEmpty() || classId.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }
        addStudent(name, studentNumber, classId);
    }

    public void addStudent(String name, String studentNumber, String classId) {
        try {
            String sql = "INSERT INTO students (name, student_number, class_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, studentNumber);
            stmt.setString(3, classId);
            stmt.executeUpdate();
            showAlert("Success", "Student added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add student: " + e.getMessage());
        }
    }

    // 11. View all Modules
    public void viewAllModules() {
        try {
            String sql = "SELECT * FROM modules";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            StringBuilder modulesList = new StringBuilder();
            while (rs.next()) {
                modulesList.append("Module Name: ").append(rs.getString("module_name"))
                        .append(", Code: ").append(rs.getString("code"))
                        .append("\n");
            }
            if (modulesList.length() == 0) {
                showAlert("Info", "No modules found.");
            } else {
                showAlert("Modules", modulesList.toString());
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to retrieve modules: " + e.getMessage());
        }
    }

    // View all Lecturers
    public void viewAllLecturers(ActionEvent actionEvent) {
        try {
            String sql = "SELECT * FROM lecturers";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            StringBuilder lecturersList = new StringBuilder();
            while (rs.next()) {
                lecturersList.append("Name: ").append(rs.getString("name"))
                        .append(", Employee Number: ").append(rs.getString("employee_number"))
                        .append(", Department: ").append(rs.getString("department"))
                        .append("\n");
            }
            if (lecturersList.length() == 0) {
                showAlert("Info", "No lecturers found.");
            } else {
                showAlert("Lecturers", lecturersList.toString());
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to retrieve lecturers: " + e.getMessage());
        }
    }

    // View all Semesters
    public void viewAllSemesters(ActionEvent actionEvent) {
        try {
            String sql = "SELECT * FROM semesters";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            StringBuilder semestersList = new StringBuilder();
            while (rs.next()) {
                semestersList.append("Semester Name: ").append(rs.getString("semester_name"))
                        .append("\n");
            }
            if (semestersList.length() == 0) {
                showAlert("Info", "No semesters found.");
            } else {
                showAlert("Semesters", semestersList.toString());
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to retrieve semesters: " + e.getMessage());
        }
    }

    // View all Students
    public void viewAllStudents(ActionEvent actionEvent) {
        try {
            String sql = "SELECT * FROM students";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            StringBuilder studentsList = new StringBuilder();
            while (rs.next()) {
                studentsList.append("Name: ").append(rs.getString("name"))
                        .append(", Student Number: ").append(rs.getString("student_number"))
                        .append(", Class ID: ").append(rs.getString("class_id"))
                        .append("\n");
            }
            if (studentsList.length() == 0) {
                showAlert("Info", "No students found.");
            } else {
                showAlert("Students", studentsList.toString());
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to retrieve students: " + e.getMessage());
        }
    }

    //12 Method to assign module and class to a lecturer
    public void assignModuleAndClass(int lecturer_id, int moduleId, int classId) {
        try {
            String sql = "INSERT INTO lecturer_modules( lecturer_id,module_id, class_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, lecturer_id);
            stmt.setInt(2, moduleId);
            stmt.setInt(3, classId);
            stmt.executeUpdate();
            showAlert("Success", "Module and class assigned successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to assign module and class: " + e.getMessage());
        }
    }

    // 13. Handle Assign Module and Class Button Click
    @FXML
    public void handleAssignModuleAndClass(ActionEvent event) {
        String selectedLecturer = lecturerComboBox.getValue();
        String selectedModule = moduleComboBox.getValue();
        String selectedClass = classComboBox.getValue();

        if (selectedLecturer == null || selectedModule == null || selectedClass == null) {
            showAlert("Input Error", "Please select a lecturer, module, and class.");
            return;
        }

        // Fetch IDs for the selected lecturer, module, and class
        int lecturerId = getLecturerId(selectedLecturer);
        int moduleId = getModuleId(selectedModule);
        int classId = getClassId(selectedClass);

        assignModuleAndClass(lecturerId, moduleId, classId);
    }

    // Helper method to fetch lecturer ID by name
    private int getLecturerId(String lecturerName) {
        try {
            String sql = "SELECT lecturer_id FROM lecturers WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, lecturerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("lecturer_id");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch lecturer ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    // 12. Populate ComboBoxes with data from the database
    private void populateLecturerComboBox() {
        try {
            String sql = "SELECT name FROM lecturers";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lecturerComboBox.getItems().add(rs.getString("name"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to populate lecturers: " + e.getMessage());
        }
    }

    // Helper method to fetch module ID by name
    private int getModuleId(String moduleName) {
        try {
            String sql = "SELECT module_id FROM modules WHERE module_name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("module_id");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch module ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    // Helper method to fetch class ID by name
    private int getClassId(String className) {
        try {
            String sql = "SELECT class_id FROM classes WHERE class_name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, className);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("class_id");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch class ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    private void populateModuleComboBox() {
        try {
            String sql = "SELECT module_name FROM modules";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                moduleComboBox.getItems().add(rs.getString("module_name"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to populate modules: " + e.getMessage());
        }
    }

    private void populateClassComboBox() {
        try {
            String sql = "SELECT class_name FROM classes";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classComboBox.getItems().add(rs.getString("class_name"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to populate classes: " + e.getMessage());
        }
    }



    public void handleViewProfile(ActionEvent event) {
        try {
            // Specify the role you're checking for, e.g., "ADMIN"
            String role = "ADMIN"; // This could be dynamic based on the logged-in user
            String sql = "SELECT username, employee_number, role FROM users WHERE role = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, role); // Set the parameter for role
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                adminNameField.setText(rs.getString("username")); // Assuming the username corresponds to the admin's name
                adminEmployeeNumberField.setText(rs.getString("employee_number"));
                adminRoleField.setText(rs.getString("role"));
            } else {
                showAlert("Info", "No profile found for this admin.");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to retrieve admin profile: " + e.getMessage());
        }
    }


    @FXML
    public void handleAssignModuleToStudent(ActionEvent actionEvent) {
        // Get selected values from the ComboBoxes
        String selectedStudent = studentComboBox.getValue();
        String selectedModule = assignModuleComboBox.getValue();

        // Validate selections
        if (selectedStudent == null || selectedModule == null) {
            showAlert("Input Error", "Please select a student and a module.");
            return;
        }

        // Fetch IDs for the selected student and module
        int studentId = getStudentId(selectedStudent);
        int moduleId = getModulecode(selectedModule);

        // Ensure valid IDs were retrieved
        if (studentId == -1 || moduleId == -1) {
            showAlert("Error", "Failed to retrieve student or module ID.");
            return;
        }

        // Assign the selected module to the student
        assignModuleToStudent(studentId, moduleId);
    }

    // Method to populate studentComboBox with student names

    private void populateStudentComboBox() {
        try {
            // Query to fetch all student names
            String sql = "SELECT name FROM students";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Add each student name to the ComboBox
            while (rs.next()) {
                studentComboBox.getItems().add(rs.getString("name"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to populate student combo box: " + e.getMessage());
        }
    }

    // Method to populate assignModuleComboBox with module names
    private void populateassignModuleComboBox(){
        try {
            // Query to fetch all module names
            String sql = "SELECT module_name FROM modules";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            assignModuleComboBox.getItems().clear();
            // Add each module name to the ComboBox
            while (rs.next()) {
                assignModuleComboBox.getItems().add(rs.getString("module_name"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to populate module combo box: " + e.getMessage());
        }
    }

    // Helper method to fetch student ID by name
    private int getStudentId(String studentName) {
        try {
            String sql = "SELECT student_id FROM students WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, studentName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_id");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch student ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    // Helper method to fetch module ID by name
    protected int getModulecode(String moduleName) {
        try {
            String sql = "SELECT module_id FROM modules WHERE module_name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("module_id");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch module ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    // Method to assign the module to the student in the database
    private void assignModuleToStudent(int studentId, int moduleId) {
        try {
            String sql = "INSERT INTO student_modules (student_id, module_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setInt(2, moduleId);
            stmt.executeUpdate();

            showAlert("Success", "Module assigned to student successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to assign module to student: " + e.getMessage());
        }
    }
}







