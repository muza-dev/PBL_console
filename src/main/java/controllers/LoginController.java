package controllers;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private ImageView eyeIcon;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("❗ Email va parolni to‘liq kiritilishi kerak.");
            return;
        }

        if (UserDAO.validateLogin(email, password)) {
            boolean isAdmin = UserDAO.isAdmin(email);
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("✅ Xush kelibsiz!");

            String fxmlToLoad = isAdmin ? "/views/admin_dashboard.fxml" : "/views/user_main.fxml";

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlToLoad));
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("❌ Sahifani yuklab bo‘lmadi.");
            }
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("❌ Email yoki parol noto‘g‘ri.");
        }
    }

    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean passwordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Ko‘rishni yopamiz
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
        } else {
            // Ko‘rishni yoqamiz
            passwordTextField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
        }
        passwordVisible = !passwordVisible;
    }

}
