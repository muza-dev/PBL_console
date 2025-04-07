package controllers;

import dao.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Product;

public class UpdateQuantityController {

    @FXML private TextField quantityField;
    @FXML private Label statusLabel;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @FXML
    private void handleSave() {
        try {
            int newQty = Integer.parseInt(quantityField.getText().trim());
            if (newQty < 0) {
                statusLabel.setText("❌ Manfiy bo‘lmasligi mumkin emas!");
                return;
            }

            ProductDAO.updateQuantity(product.getId(), newQty);
            statusLabel.setText("✅ Miqdor yangilandi!");

            Stage stage = (Stage) quantityField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            statusLabel.setText("❌ Raqam kiriting!");
        }
    }
}
