package com.example.databasehelp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.sql.SQLException;
import java.util.Objects;

import static com.example.databasehelp.DataCalls.checkRequirement;
import static com.example.databasehelp.DataCalls.updateStudentCourseDetails;

public class CourseRequirementManager {
    private final ObservableList<String> completedCourseIds = FXCollections.observableArrayList();
    private ObservableList<Course> studentCourseDetailsList;

    private int studentId;

    // setter for student id
    public void setStudentId(int studentId) {
        this.studentId = studentId;
        System.out.println("Received studentId in AdvisorSheetController: " + studentId);

    }

    //setter for studentcoursedetails
    public void setStudentCourseDetails(int studentId, ObservableList<Course> studentCourseDetailsList) {
        this.studentId = studentId;
        this.studentCourseDetailsList = studentCourseDetailsList;
        System.out.println("COURSEREQUIREMENTMANAGER: got to setstudentcoursedetails");

    }

    // Method to add a course to the completed list
    public void addCompletedCourse(String courseId) {
        if (!completedCourseIds.contains(courseId)) {
            completedCourseIds.add(courseId);
        }
        System.out.println("COURSEREQUIREMENTMANAGER: got to add");

    }

    // Method to remove a course from the completed list
    public void removeCompletedCourse(String courseId) {
        completedCourseIds.remove(courseId);
        System.out.println("COURSEREQUIREMENTMANAGER: got to remove");
    }

    //checks if a specific course is completed by checking if the list contains id.
    public boolean isCourseRequirementCompleted(String courseId) {
        System.out.println("COURSEREQUIREMENTMANAGER: got to iscoursecompletion");
        return completedCourseIds.contains(courseId);
    }

    // Initializes checkboxes for first table





            private <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String id) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (id.equals(column.getId())) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }

    // Initializes checkboxes for second table
    public void initializeCompletionStatusColumn(TableView<Course> tableView, int suffix) {
        TableColumn<Course, String> completionStatusColumn = getColumn(tableView, "course_completion_" + suffix);
        completionStatusColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            // Setting all completion column, reading the studentdetails list for completion or not
            if (course != null && course.getStudentCourseDetails() != null && course.getStudentCourseDetails().getCourseCompletion() == 1) {
                addCompletedCourse(course.getCourseID());
                return new SimpleStringProperty("Completed");
            } else {
                return new SimpleStringProperty("Incomplete");
            }
        });

        completionStatusColumn.setCellFactory(column -> new TableCell<Course, String>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Completed", "Incomplete"));

            {
                choiceBox.setPrefWidth(20);
                choiceBox.setStyle("-fx-font-size: 15px");

            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(choiceBox);
                    Course course = getTableView().getItems().get(getIndex());
                    if (course != null && course.getStudentCourseDetails() != null) {
                        // Initialize the ChoiceBox with the correct completion status
                        choiceBox.setValue(course.getStudentCourseDetails().getCourseCompletion() == 1 ? "Completed" : "Incomplete");

                        // Handle actions when the user changes the completion status
                        choiceBox.setOnAction(event -> {
                            String value = choiceBox.getValue();
                            if (Objects.equals(value, "Completed")) {
                                // Handle completion
                                String requiredID = checkRequirement(course.getCourseID());
                                if (requiredID == null || completedCourseIds.contains(requiredID)) {
                                    course.getStudentCourseDetails().setCourseCompletion(1);
                                    if (!isCourseRequirementCompleted(course.getCourseID())) {
                                        System.out.println("Added to courselist");
                                        addCompletedCourse(course.getCourseID());
                                    }
                                    System.out.println("course set to complete");
                                    setStyle("-fx-background-color: transparent;");
                                } else {
                                    choiceBox.setValue("Incomplete");
                                    setStyle("-fx-background-color: red;");
                                }
                            } else {
                                // Handle incompletion
                                course.getStudentCourseDetails().setCourseCompletion(0);
                                if (isCourseRequirementCompleted(course.getCourseID())) {
                                    removeCompletedCourse(course.getCourseID());
                                    System.out.println("course removed");
                                }
                                System.out.println("course set to incomplete");
                            }
                        });
                    }
                }
            }
});
    }
    }