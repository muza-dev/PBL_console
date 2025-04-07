package controllers;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class RegisterController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private Label messageLabel;


    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.isVisible() ? passwordField.getText() : passwordTextField.getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❗ Hamma maydonlar to‘ldirilishi shart!");
            return;
        }

        if (password.length() < 8) {
            messageLabel.setText("❗ Parol kamida 8 ta belgidan iborat bo‘lishi kerak.");
            return;
        }

        // Email borligini oldindan tekshirish
        if (UserDAO.emailExists(email)) {
            messageLabel.setText("❌ Bu email allaqachon ro‘yxatdan o‘tgan.");
            return;
        }

        // Bazaga yozish
        boolean success = UserDAO.registerUser(fullName, email, password);

        if (success) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("✅ Ro‘yxatdan o‘tish muvaffaqiyatli!");

            // (Ixtiyoriy) avtomatik login yoki login sahifasiga o‘tish
        } else {
            messageLabel.setText("❌ Foydalanuvchini saqlashda xatolik yuz berdi.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
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
