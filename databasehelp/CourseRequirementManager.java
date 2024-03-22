package com.example.databasehelp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

public class CourseRequirementManager {
    private final ObservableList<String> completedCourseIds = FXCollections.observableArrayList();

    // Method to add a course to the completed list
    public void addCompletedCourse(String courseId) {
        if (!completedCourseIds.contains(courseId)) {
            completedCourseIds.add(courseId);
        }
    }

    // Method to remove a course from the completed list
    public void removeCompletedCourse(String courseId) {
        completedCourseIds.remove(courseId);
    }

    // Method to check if a course requirement is completed
    public boolean isCourseRequirementCompleted(String courseId) {
        return completedCourseIds.contains(courseId);
    }

    // Method to initialize listeners for checkbox changes in the table
    public void initializeCheckBoxListeners(TableView<Course> tableView) {
        // Assuming you have a column named "completedColumn" for the checkbox
        TableColumn<Course, Boolean> completedColumn = getColumn(tableView, "completedColumn");
        if (completedColumn != null) {
            completedColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return course.getStudentCourseDetails().isCourseCompletion();
                } else {
                    return new SimpleBooleanProperty(false);
                }
            });
            completedColumn.setCellFactory(column -> new CheckBoxTableCell<>());
            completedColumn.setOnEditCommit(event -> {
                Course course = event.getRowValue();
                boolean newValue = event.getNewValue();
                if (newValue) {
                    addCompletedCourse(course.getCourseID());
                } else {
                    removeCompletedCourse(course.getCourseID());
                }
            });
        }
    }

    // Helper method to get a column from a TableView by its ID
    private <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String id) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (id.equals(column.getId())) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }
}
