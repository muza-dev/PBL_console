package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UserMainController {

    @FXML
    private void goToProductList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/user_product_list.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mahsulotlar ro‘yxati");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToCart(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/cart.fxml")); // Agar hali yo'q bo‘lsa, keyin yozamiz
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Savat");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tizimga kirish");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
