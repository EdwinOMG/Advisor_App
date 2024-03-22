package com.example.databasehelp;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class StudentCourseDetailsCellValueFactory implements Callback<TableColumn.CellDataFeatures<StudentCourseDetails, String>, ObservableValue<String>> {
    private final String property;

    public StudentCourseDetailsCellValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentCourseDetails, String> cellData) {
        Object value = cellData.getValue();

        // Check the type before casting
        if (value instanceof StudentCourseDetails) {
            StudentCourseDetails details = (StudentCourseDetails) value;

            switch (property) {
                case "courseNotes":
                    return new SimpleStringProperty(details.getCourseNotes());
                case "courseTerm":
                    return new SimpleStringProperty(details.getCourseTerm());
                case "courseGrade":
                    return new SimpleStringProperty(details.getCourseGrade());
                case "courseCompletion":
                    return new SimpleBooleanProperty(details.isCourseCompletion()).asString();
                default:
                    return new SimpleStringProperty("");
            }
        } else {
            return new SimpleStringProperty("");
        }
    }
}