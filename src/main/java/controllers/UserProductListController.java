package controllers;

import database.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.stage.Stage;
import models.Product;

import java.sql.*;

public class UserProductListController {

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Ustunlarni moslashtirish
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Ustun joyini o‘zgartirishni taqiqlash
        productTable.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                TableHeaderRow header = (TableHeaderRow) productTable.lookup("TableHeaderRow");
                if (header != null) {
                    header.reorderingProperty().set(false);
                }
            }
        });

        loadProducts(); // Mahsulotlarni yuklash
    }

    private void loadProducts() {
        productList.clear();

        String sql = "SELECT id, name, description, price, quantity, category FROM products";

        try (Connection conn = DBUtil.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                productList.add(product);
            }

            productTable.setItems(productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddToCart() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String sql = "INSERT INTO cart(product_id, product_name, price) VALUES (?, ?, ?)";

            try (Connection conn = DBUtil.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, selected.getId());
                pstmt.setString(2, selected.getName());
                pstmt.setDouble(3, selected.getPrice());
                pstmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "✅ Savatga qo‘shildi", "Tanlangan mahsulot savatga qo‘shildi.");

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "❌ Xatolik", "Ma'lumotlar bazasiga yozishda xatolik yuz berdi.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "⚠ Diqqat", "Iltimos, mahsulotni tanlang.");
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/user_main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Foydalanuvchi Paneli");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
