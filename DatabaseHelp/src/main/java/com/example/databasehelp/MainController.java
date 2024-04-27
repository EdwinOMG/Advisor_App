package com.example.databasehelp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static com.example.databasehelp.DataCalls.*;
//
public class MainController {
    @FXML
    private ChoiceBox<String> major_box;
    @FXML
    private TextField text_id;

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_email;

    @FXML
    private TextField text_address;

    @FXML
    private TextField text_number;

    @FXML
    private Button addbutton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField selectStudentID;
    @FXML
    private Label warningSelectLabel;
    @FXML
    private Label warningAddLabel;
    private int majorNumber;

    private int idNum;





    @FXML
    private void initialize() {
        major_box.getItems().addAll("Computer Programming", "Respiratory Care");
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        String studentId = text_id.getText();
        warningAddLabel.setText("");
        text_id.getStyleClass().remove("red-text-field");
        text_email.getStyleClass().remove("red-text-field");
        text_number.getStyleClass().remove("red-text-field");
        major_box.getStyleClass().remove("red-text-field");

        try {
            idNum = Integer.parseInt(studentId);
        } catch (NumberFormatException e) {
            System.err.println("Invalid Student ID: " + studentId);
            warningAddLabel.setText("Invalid Student ID");
            text_id.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            return;
        }//converting here cause textfield has to be string

        //grabbing all textfields on button press
        String studentName = text_name.getText();
        String studentEmail = text_email.getText();
        String studentAddress = text_address.getText();
        String studentNumber = text_number.getText();
        String selectedMajor = major_box.getValue();
        // can easily edit this, so when I add new majors just add numbers
        if ("Computer Programming".equals(selectedMajor)){
            majorNumber = 1;
        }
        else if("Respiratory Care".equals(selectedMajor)){
            majorNumber = 2;
        }
        else {
            warningAddLabel.setText("No major selected");
            major_box.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            return;
        }
// Checks if valid email address
        if (studentEmail.indexOf('@') == -1){
            warningAddLabel.setText("Not a valid email address");
            text_email.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");

            return;
        }
        // Checking valid phone number
        if (validatePhoneNumber(studentNumber) == false){
            warningAddLabel.setText("Number must be XXX-XXX-XXXX");
            System.out.println("In validatephonenumber if statemnt");
            text_number.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            return;
        }
        //Checking if fields are empty
        if (studentId.isEmpty() || studentEmail.isEmpty() || studentAddress.isEmpty() || studentNumber.isEmpty() || studentName.isEmpty()){
            warningAddLabel.setText("All info must be filled out.");
        }
        // Print or use the retrieved values as needed
        System.out.println("Student ID: " + idNum);
        System.out.println("Student Name: " + studentName);
        System.out.println("Student Email: " + studentEmail);
        System.out.println("Student Address: " + studentAddress);
        System.out.println("Student Number: " + studentNumber);
        System.out.println("Selected Major: " + majorNumber);

        DataCalls.insertStudent(idNum, studentName, studentNumber, studentEmail, studentAddress, majorNumber);
        //clearing text fields after inserting the data
        text_id.setText("");
        text_name.setText("");
        text_email.setText("");
        text_address.setText("");
        text_number.setText("");

        retrieveStudentID studentIdInstance = new retrieveStudentID();
        studentIdInstance.setStudentId(idNum);
        System.out.println("maincontroller" + idNum);

        if (majorNumber == 1) {
            openAdvisorsheetGUI(idNum);
        }
        else if (majorNumber == 2){
            openAdvisorsheetRespiratoryGUI(idNum);
        }
    }

        private void openAdvisorsheetGUI(int idNum) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/databasehelp/Advisor.fxml"));
                Parent root = loader.load();

                AdvisorSheetController advisorSheetController = loader.getController();

                advisorSheetController.setStudentId(idNum);

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    private void openAdvisorsheetRespiratoryGUI(int idNum) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/databasehelp/respiratory.fxml"));
            Parent root = loader.load();

            respiratorySheetController respiratorySheetController = loader.getController();

            respiratorySheetController.setStudentId(idNum);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSearchButton(ActionEvent event) {
        System.out.println("Got into search button");
        String searchID = selectStudentID.getText();
        if (searchID.isEmpty()) {
            // Handle the case where input is empty
            warningSelectLabel.setText("Please enter a student ID");
            selectStudentID.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            return;
        }
        try {
            int idNum = Integer.parseInt(searchID);
            boolean exist = doesStudentExist(idNum);
            selectStudentID.getStyleClass().remove("red-text-field");

            if (exist) {
                warningSelectLabel.setText(null);

                ObservableList<Course> courseIdList = DataCalls.selectCoursesByStudent(idNum);
                System.out.println(courseIdList);

                // Pass the student ID and course details to the appropriate controller
                int major = DataCalls.getMajorFromStudent(idNum);
                if (major == 1){
                    openSelectedAdvisorsheetGUI(idNum, courseIdList);

                }
                else if (major == 2){
                    openSelectedRespiratoryGUI(idNum, courseIdList);
                }
                else {
                    System.out.println("Chat theres only two majors how could they do this...");
                }

                selectStudentID.setText("");
            } else {
                warningSelectLabel.setText("NOT A VALID STUDENT ID");
                selectStudentID.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");

                System.out.println("Empty search student id textField");
            }
        } catch (NumberFormatException e) {
            // Handle the case where input cannot be parsed to an integer
            warningSelectLabel.setText("Invalid input. Please enter a valid student ID");
            selectStudentID.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        }
    }

    private void openSelectedAdvisorsheetGUI(int idNum, ObservableList<Course> studentCourseDetailsList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/databasehelp/Advisor.fxml"));
            Parent root = loader.load();

            AdvisorSheetController advisorSheetController = loader.getController();

            advisorSheetController.setStudentCourseDetails(studentCourseDetailsList);
            advisorSheetController.setStudentId(idNum);
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    private void openSelectedRespiratoryGUI(int idNum, ObservableList<Course> studentCourseDetailsList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/databasehelp/respiratory.fxml"));
            Parent root = loader.load();

            respiratorySheetController respiratorySheetController = loader.getController();

            respiratorySheetController.setStudentCourseDetails(studentCourseDetailsList);
            respiratorySheetController.setStudentId(idNum);
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    private static boolean validatePhoneNumber(String phoneNumber){
        System.out.println("333-222-2222".matches("\\d{3}-\\d{3}-\\d{4}"));
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }
}