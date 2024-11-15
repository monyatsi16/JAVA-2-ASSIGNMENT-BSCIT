package assignment;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private DatabaseHelper databaseHelper = new DatabaseHelper();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if username and password are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.");
            return;
        }

        // Attempt to login
        User user = databaseHelper.login(username, password);

        if (user != null) {
            loadDashboard(user.getRole());
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void loadDashboard(String role) {
        try {
            FXMLLoader loader;
            String fxmlFile = "";

            // Load the appropriate dashboard based on user role
            switch (role) {
                case "ADMIN":
                    fxmlFile = "admin.fxml";
                    break;
                case "LECTURER":
                    fxmlFile = "lecturer.fxml"; // Add your lecturer FXML file here
                    break;
                case "PRINCIPALLECTURER":
                    fxmlFile = "prl.fxml"; // Add your Principal Lecturer FXML file here
                    break;
                default:
                    showAlert("Access Denied", "You do not have permission to access this application.");
                    return; // Exit if the role is not recognized
            }

            loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Dashboard - " + role); // Optional: Change title based on role
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the dashboard.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(content);
        alert.showAndWait();
    }
}
