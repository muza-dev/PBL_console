package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AdminDashboardController {

    @FXML
    public void openAddProductWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/add_product.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mahsulot qo‘shish");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openProductList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/product_list.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mahsulotlar ro‘yxati");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/delete_product.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mahsulotni o‘chirish");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openOrderList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/order_list.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyurtmalar ro‘yxati");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tizimga kirish");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteProduct(javafx.event.ActionEvent actionEvent) {
    }

    public void openProductList(javafx.event.ActionEvent actionEvent) {
    }
}