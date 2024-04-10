package com.example.databasehelp;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Objects;

public class TermGradeColumnMaker {
    public static void initializeTermColumn(TableView<Course> tableView, int suffix) {
        TableColumn<Course, String> termColumn = getColumn(tableView, "course_term_" + suffix);
        termColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            return new SimpleStringProperty(course.getStudentCourseDetails().getCourseTerm());
        });

        termColumn.setCellFactory(column -> new TableCell<Course, String>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("FA", "WI", "SP", "SU", "WE"));

            {
                System.out.println("Should of set terms");
                choiceBox.setPrefWidth(20);
                choiceBox.setStyle("-fx-font-size: 15px");
                choiceBox.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    String Value = choiceBox.getValue();
                    course.getStudentCourseDetails().setCourseTerm(Value);
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    choiceBox.setValue(item);
                    setGraphic(choiceBox);
                }
            }
        });
        tableView.getColumns().add(termColumn);


    }

    private static <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String id) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (id.equals(column.getId())) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }

    public static void initializeGradeColumn(TableView<Course> tableView, int suffix) {
        TableColumn<Course, String> gradeColumn = getColumn(tableView, "course_grade_" + suffix);
        gradeColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            return new SimpleStringProperty(course.getStudentCourseDetails().getCourseGrade());
        });

        gradeColumn.setCellFactory(column -> new TableCell<Course, String>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-"));

            {
                System.out.println("Should of set grades");
                choiceBox.setPrefWidth(20);
                choiceBox.setStyle("-fx-font-size: 15px");
                choiceBox.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    String Value = choiceBox.getValue();
                    course.getStudentCourseDetails().setCourseGrade(Value);
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    choiceBox.setValue(item);
                    setGraphic(choiceBox);
                }
            }
        });
        tableView.getColumns().add(gradeColumn);


    }
}
