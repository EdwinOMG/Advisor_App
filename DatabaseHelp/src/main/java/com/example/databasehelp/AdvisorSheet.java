package com.example.databasehelp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvisorSheet extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/databasehelp/Advisor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 730, 850);
        // Lock stage size
        stage.setResizable(false);






        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
