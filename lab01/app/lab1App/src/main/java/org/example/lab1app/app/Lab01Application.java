package org.example.lab1app.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Lab01Application extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Object root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/lab1app/mainPage/lab-01-main.fxml")));
            Scene scene = new Scene((Parent) root);
            stage.setTitle("Lab 1 App");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML file" + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}