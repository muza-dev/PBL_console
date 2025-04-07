package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void createCartTableIfNotExists() {
        String url = "jdbc:sqlite:resources/database/ecommerce.db";

        String sql = """
            CREATE TABLE IF NOT EXISTS cart (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                product_id INTEGER,
                product_name TEXT,
                price REAL
            );
        """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Cart jadvali tekshirildi/yaratildi.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
