package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardController {

    @FXML
    private Label userCountLabel;

    private Connection connect() {
        // SQLite bilan bog‘lanish
        String url = "jdbc:sqlite:resources/database/ecommerce.db"; // real yo'lni yozing
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @FXML
    public void initialize() {
        loadUserCount();
    }

    @FXML
    private void refreshUserCount() {
        loadUserCount();
    }

    private void loadUserCount() {
        String sql = "SELECT COUNT(*) AS total FROM users";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int count = rs.getInt("total");
                userCountLabel.setText("Umumiy foydalanuvchilar soni: " + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
            userCountLabel.setText("Xatolik yuz berdi");
        }
    }
}
