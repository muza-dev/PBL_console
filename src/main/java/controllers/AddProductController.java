package controllers;

import dao.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;

public class AddProductController {

    @FXML private TextField nameField;
    @FXML private TextField descField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField categoryField;
    @FXML private Label statusLabel;

    private Connection connect() {
        String url = "jdbc:sqlite:resources/database/ecommerce.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @FXML
    private void handleAddProduct() {
        try {
            String name = nameField.getText();
            String desc = descField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryField.getText();

            if (name.isEmpty() || price < 0 || quantity < 0) {
                statusLabel.setText("❌ Ma’lumotlar to‘liq emas yoki xato!");
                return;
            }

            ProductDAO.insertProduct(name, desc, price, quantity, category);
            statusLabel.setText("✅ Mahsulot muvaffaqiyatli qo‘shildi!");
        } catch (Exception e) {
            statusLabel.setText("❌ Xatolik: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
