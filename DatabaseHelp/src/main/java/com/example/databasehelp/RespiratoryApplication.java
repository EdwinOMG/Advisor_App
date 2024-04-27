package com.example.databasehelp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RespiratoryApplication extends Application {
    private ChoiceBox<String> major_box;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(RespiratoryApplication.class.getResource("/com/example/databasehelp/respiratory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 718, 850);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }   

    public static void main(String[] args) {
        launch();
    }
}