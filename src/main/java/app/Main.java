package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.DatabaseInitializer; // <-- bu MUHIM

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Ma'lumotlar bazasini tayyorlash
        DatabaseInitializer.createCartTableIfNotExists();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            primaryStage.setTitle("Tizimga kirish");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
