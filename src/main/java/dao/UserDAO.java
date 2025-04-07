package dao;

import database.DBConnection;
import database.DBUtil;

import java.sql.*;

public class UserDAO {

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "is_admin INTEGER DEFAULT 1" +
                ");";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

            // Check if admin exists
            if (!adminExists()) {
                insertAdmin();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertAdmin() {
        String sql = "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Admin");
            stmt.setString(2, "admin@gmail.com");
            stmt.setString(3, "admin123");
            stmt.setInt(4, 1);
            stmt.executeUpdate();
            System.out.println("✅ Admin foydalanuvchi bazaga qo‘shildi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static boolean adminExists() {
        String sql = "SELECT * FROM users WHERE email = 'admin@gmail.com'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAdmin(String email) {
        String sql = "SELECT is_admin FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("is_admin") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String fullName, String email, String password) {
        String sql = "INSERT INTO users(full_name, email, password, is_admin) VALUES (?, ?, ?, 0)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            // UNIQUE constraint failed: users.email
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("⚠️ Email allaqachon ro‘yxatdan o‘tgan.");
            } else {
                System.out.println("❗ Boshqa xatolik: " + e.getMessage());
            }
            return false;
        }
    }

    public static boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeQuery().next(); // agar natija bor bo‘lsa, true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int countUsers() {
        String query = "SELECT COUNT(*) FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
