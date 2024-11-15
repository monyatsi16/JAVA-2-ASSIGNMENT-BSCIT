package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHelper {
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/academic";
        String user = "root";
        String password = ""; // Ensure your password is correct

        Connection conn = null;
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully.");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return conn;
    }

    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?"; // username and password are VARCHAR

        System.out.println("Trying to log in with username: " + username + " and password: " + password);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username); // Assuming username is VARCHAR in the database
            pstmt.setString(2, password);  // Assuming password is also VARCHAR in the database

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role"); // role should be VARCHAR as well
                user = new User(username, role); // Assuming User constructor takes username and role
                System.out.println("Login successful for user: " + username);
            } else {
                System.out.println("Login failed. No matching user found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addLecturer(String name, String id) {
        String sql = "INSERT INTO lecturers (name, id) VALUES (?, ?)"; // name and id are VARCHAR

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name); // Assuming name is VARCHAR in the database
            pstmt.setString(2, id);   // Assuming id is also VARCHAR in the database
            pstmt.executeUpdate();
            System.out.println("Lecturer added successfully: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
