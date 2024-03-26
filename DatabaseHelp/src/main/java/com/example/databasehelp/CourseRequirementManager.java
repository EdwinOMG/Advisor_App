package com.example.databasehelp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

public class CourseRequirementManager {
    private final ObservableList<String> completedCourseIds = FXCollections.observableArrayList();
    private ObservableList<Course> studentCourseDetailsList;

    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
        System.out.println("Received studentId in AdvisorSheetController: " + studentId);

    }

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

    public boolean isCourseRequirementCompleted(String courseId) {
        System.out.println("COURSEREQUIREMENTMANAGER: got to iscoursecompletion");

        return completedCourseIds.contains(courseId);

    }

    public void initializeCheckBoxListeners(TableView<Course> tableView) {
        TableColumn<Course, Boolean> courseCompletionColumn = getColumn(tableView, "courseCompletion");
        System.out.println("INITIALIZECHECKBOXLISTENERS: GOT HERE");
        if (courseCompletionColumn != null) {
            courseCompletionColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleBooleanProperty(course.getStudentCourseDetails().isCourseCompletion());
                } else {
                    return new SimpleBooleanProperty(false);
                }
            });

            courseCompletionColumn.setCellFactory(column -> {
                CheckBoxTableCell<Course, Boolean> cell = new CheckBoxTableCell<>();
                cell.setSelectedStateCallback(param -> {
                    Course editedCourse = tableView.getItems().get(cell.getIndex());
                    StudentCourseDetails details = editedCourse.getStudentCourseDetails();
                    if (details != null) {
                        boolean newValue = (param != 0);
                        details.setCourseCompletion(newValue);
                    }
                    return cell.selectedProperty();
                });
                return cell;
            });
            System.out.println("Course completion changed for all courses");

        }
    }
    private <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String id) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (id.equals(column.getId())) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }

    public void initializeSecondCheckBoxListeners(TableView<Course> tableView, int suffix) {
        TableColumn<Course, Boolean> courseCompletionColumn = getColumn(tableView, "course_completion_" + suffix);
        if (courseCompletionColumn != null) {
            courseCompletionColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleBooleanProperty(course.getStudentCourseDetails().isCourseCompletion());
                } else {
                    return new SimpleBooleanProperty(false);
                }
            });

            courseCompletionColumn.setCellFactory(column -> {
                CheckBoxTableCell<Course, Boolean> cell = new CheckBoxTableCell<>();

                cell.setSelectedStateCallback(param -> {
                    Course editedCourse = tableView.getItems().get(cell.getIndex());
                    StudentCourseDetails details = editedCourse.getStudentCourseDetails();
                    if (details != null) {
                        boolean newValue = (param != 0);
                        details.setCourseCompletion(newValue);
                    }
                    return cell.selectedProperty();
                });

                return cell;
            });
        }
        System.out.println("Course completion changed for all courses");

    }
}


