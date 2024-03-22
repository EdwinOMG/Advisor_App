package com.example.databasehelp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.ArrayList;

import static com.example.databasehelp.DataCalls.updateStudentCourseDetails;

public class AdvisorSheetController {
    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private TableView<Course> pre_table;
    @FXML
    private TableView<Course> first_table;
    @FXML
    private TableView<Course> second_table;
    @FXML
    private TableView<Course> third_table;
    @FXML
    private TableView<Course> fourth_table;


    @FXML
    private TableColumn<Course, Boolean> courseCompletion;
    @FXML
    private TableColumn<Course, String> course_id;
    @FXML
    private TableColumn<Course, String> course_name;
    @FXML
    private TableColumn<Course, Integer> course_credits;
    @FXML
    private TableColumn<Course, String> course_term;
    @FXML
    private TableColumn<Course, String> course_grade;
    @FXML
    private TableColumn<Course, String> course_notes;

    @FXML
    private TableColumn<Course, Boolean> course_completion_2;
    @FXML
    private TableColumn<Course, String> course_id_2;
    @FXML
    private TableColumn<Course, String> course_name_2;
    @FXML
    private TableColumn<Course, Integer> course_credits_2;
    @FXML
    private TableColumn<Course, String> course_term_2;
    @FXML
    private TableColumn<Course, String> course_grade_2;
    @FXML
    private TableColumn<Course, String> course_notes_2;

    @FXML
    private TableColumn<Course, Boolean> course_completion_3;
    @FXML
    private TableColumn<Course, String> course_id_3;
    @FXML
    private TableColumn<Course, String> course_name_3;
    @FXML
    private TableColumn<Course, Integer> course_credits_3;
    @FXML
    private TableColumn<Course, String> course_term_3;
    @FXML
    private TableColumn<Course, String> course_grade_3;
    @FXML
    private TableColumn<Course, String> course_notes_3;

    @FXML
    private TableColumn<Course, Boolean> course_completion_4;
    @FXML
    private TableColumn<Course, String> course_id_4;
    @FXML
    private TableColumn<Course, String> course_name_4;
    @FXML
    private TableColumn<Course, Integer> course_credits_4;
    @FXML
    private TableColumn<Course, String> course_term_4;
    @FXML
    private TableColumn<Course, String> course_grade_4;
    @FXML
    private TableColumn<Course, String> course_notes_4;

    @FXML
    private TableColumn<Course, Boolean> course_completion_5;
    @FXML
    private TableColumn<Course, String> course_id_5;
    @FXML
    private TableColumn<Course, String> course_name_5;
    @FXML
    private TableColumn<Course, Integer> course_credits_5;
    @FXML
    private TableColumn<Course, String> course_term_5;
    @FXML
    private TableColumn<Course, String> course_grade_5;
    @FXML
    private TableColumn<Course, String> course_notes_5;
    private int studentId;


    public void setStudentId(int studentId) {
        this.studentId = studentId;
        System.out.println("Received studentId in AdvisorSheetController: " + studentId);

    }
    public void setStudentCourseDetails(ObservableList<Course> studentCourseDetailsList) {
        for (Course course : studentCourseDetailsList) {
            int semester = course.getCourseSemester();
            TableView<Course> targetTable = null;

            switch (semester) {
                case 1:
                    targetTable = first_table;
                    break;
                case 2:
                    targetTable = second_table;
                    break;
                case 3:
                    targetTable = third_table;
                    break;
                case 4:
                    targetTable = fourth_table;
                    break;
                default:
                    targetTable = pre_table;
                    break;
            }

            if (targetTable != null) {
                replaceCourseInTable(targetTable, course);
            }
        }
    }

    private void replaceCourseInTable(TableView<Course> table, Course newCourse) {
        ObservableList<Course> items = table.getItems();

        for (Course oldCourse : new ArrayList<>(items)) {
            if (oldCourse.getCourseID().equals(newCourse.getCourseID())) {
                items.remove(oldCourse);
                break;
            }
        }

        // Add the new course to the table
        items.add(newCourse);
    }

    @FXML
    private void initialize() {


        //Load images
        Image image1 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.15.23 PM.png").toExternalForm());
        Image image2 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.15.31 PM.png").toExternalForm());
        Image image3 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.25.56 PM.png").toExternalForm());
        Image image4 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.41.31 PM.png").toExternalForm());
        Image image5 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.41.46 PM.png").toExternalForm());
        Image image6 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 5.12.37 PM.png").toExternalForm());
        Image image7 = new Image(getClass().getResource("/Images/Screenshot 2024-02-20 at 4.42.13 PM.png").toExternalForm());

        // Set images to ImageViews
        imageView1.setImage(image1);
        imageView2.setImage(image2);
        imageView3.setImage(image3);
        imageView4.setImage(image4);
        imageView5.setImage(image5);
        imageView6.setImage(image6);
        imageView7.setImage(image7);


        //grabbing all the classes in semesters, adding them to observable list for the tables
        ObservableList<Course> semester1Courses = DataCalls.selectCoursesBySemester(studentId, 1);
        ObservableList<Course> coursesList = FXCollections.observableArrayList(semester1Courses);

        ObservableList<Course> semester2Courses = DataCalls.selectCoursesBySemester(studentId, 2);
        ObservableList<Course> semester2List = FXCollections.observableArrayList(semester2Courses);

        ObservableList<Course> semester3Courses = DataCalls.selectCoursesBySemester(studentId, 3);
        ObservableList<Course> semester3List = FXCollections.observableArrayList(semester3Courses);

        ObservableList<Course> semester4Courses = DataCalls.selectCoursesBySemester(studentId, 4);
        ObservableList<Course> semester4List = FXCollections.observableArrayList(semester4Courses);

        ObservableList<Course> preReqCourses = DataCalls.selectCoursesBySemester(studentId, 5); //I need to add the pre reqs to database, 5 will be the semester for it
        ObservableList<Course> preReqList = FXCollections.observableArrayList(preReqCourses);


        TableColumn<Course, Boolean> courseCompletionColumn = getColumn(first_table, "courseCompletion");
        TableColumn<Course, String> courseIdColumn = getColumn(first_table, "course_id");
        TableColumn<Course, String> courseNameColumn = getColumn(first_table, "course_name");
        TableColumn<Course, Integer> courseCreditsColumn = getColumn(first_table, "course_credits");
        TableColumn<Course, String> courseTermColumn = getColumn(first_table, "course_term");
        TableColumn<Course, String> courseGradeColumn = getColumn(first_table, "course_grade");
        TableColumn<Course, String> courseNotesColumn = getColumn(first_table, "course_notes");


        courseCompletionColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            if (course.getStudentCourseDetails() != null) {
                return new SimpleBooleanProperty(course.getStudentCourseDetails().isCourseCompletion());
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        courseCompletionColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        courseCompletionColumn.setOnEditCommit(event -> {
            Boolean newCompletion = event.getNewValue();
            int rowIndex = event.getTablePosition().getRow();
            Course editedCourse = first_table.getItems().get(rowIndex);
            StudentCourseDetails details = editedCourse.getStudentCourseDetails();
            details.setCourseCompletion(newCompletion);
        });


        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));

        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCreditsColumn.setCellValueFactory(new PropertyValueFactory<>("courseCredits"));

        courseNotesColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            if (course.getStudentCourseDetails() != null) {
                return new SimpleStringProperty(course.getStudentCourseDetails().getCourseNotes());
            } else {
                return new SimpleStringProperty("");
            }
        });
        courseNotesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        setCellFactoryForStringColumn(courseNotesColumn);

        courseTermColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            if (course.getStudentCourseDetails() != null) {
                return new SimpleStringProperty(course.getStudentCourseDetails().getCourseTerm());
            } else {
                return new SimpleStringProperty("");
            }
        });
        courseTermColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        setCellFactoryForStringColumn(courseTermColumn);

        courseGradeColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            if (course.getStudentCourseDetails() != null) {
                return new SimpleStringProperty(course.getStudentCourseDetails().getCourseGrade());
            } else {
                return new SimpleStringProperty("");
            }
        });
        courseGradeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        setCellFactoryForStringColumn(courseGradeColumn);


        first_table.setEditable(true);
        first_table.setItems(coursesList);

        //This uses the method to set the tables and the edit on commit function
        setupTableColumns(pre_table, preReqList, "5");
        setupTableColumns(second_table, semester2List, "2");
        setupTableColumns(third_table, semester3List, "3");
        setupTableColumns(fourth_table, semester4List, "4");

    }


    @FXML
    private void handleSaveButton(ActionEvent event) {
        try {
            System.out.println("Loaded into save button method");
            //grabbing all courses in each table.
            ObservableList<Course> allCourses = first_table.getItems();
            ObservableList<Course> secondCourses = second_table.getItems();
            ObservableList<Course> thirdCourses = third_table.getItems();
            ObservableList<Course> fourthCourses = fourth_table.getItems();
            ObservableList<Course> fifthCourses = pre_table.getItems();

            //Goes through each course, if null in all it won't insert, if anything is not null it will insert it into the database.
            modifyStudentCourseDetails(allCourses);
            modifyStudentCourseDetails(secondCourses);
            modifyStudentCourseDetails(thirdCourses);
            modifyStudentCourseDetails(fourthCourses);
            modifyStudentCourseDetails(fifthCourses);

            System.out.println("Save operation completed for all items in the table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupTableColumns(TableView<Course> table,ObservableList<Course> courses, String suffix) {
        try {
            TableColumn<Course, Boolean> courseCompletionColumn = getColumn(table, "course_completion_" + suffix);
            TableColumn<Course, String> courseIdColumn = getColumn(table, "course_id_" + suffix);
            TableColumn<Course, String> courseNameColumn = getColumn(table, "course_name_" + suffix);
            TableColumn<Course, Integer> courseCreditsColumn = getColumn(table, "course_credits_" + suffix);
            TableColumn<Course, String> courseTermColumn = getColumn(table, "course_term_" + suffix);
            TableColumn<Course, String> courseGradeColumn = getColumn(table, "course_grade_" + suffix);
            TableColumn<Course, String> courseNotesColumn = getColumn(table, "course_notes_" + suffix);

            // Ensure the columns are not null before adding them to the table
            courseCompletionColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleBooleanProperty(course.getStudentCourseDetails().isCourseCompletion());
                } else {
                    return new SimpleBooleanProperty(false);
                }
            });
            courseCompletionColumn.setCellFactory(column -> new CheckBoxTableCell<>());
            courseCompletionColumn.setOnEditCommit(event -> {
                Boolean newCompletion = event.getNewValue();
                int rowIndex = event.getTablePosition().getRow();
                Course editedCourse = table.getItems().get(rowIndex);
                StudentCourseDetails details = editedCourse.getStudentCourseDetails();
                details.setCourseCompletion(newCompletion);
            });


            courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));

            courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            courseCreditsColumn.setCellValueFactory(new PropertyValueFactory<>("courseCredits"));

            courseNotesColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleStringProperty(course.getStudentCourseDetails().getCourseNotes());
                } else {
                    return new SimpleStringProperty("");
                }
            });
            courseNotesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            setCellFactoryForStringColumn(courseNotesColumn);

            courseTermColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleStringProperty(course.getStudentCourseDetails().getCourseTerm());
                } else {
                    return new SimpleStringProperty("");
                }
            });
            courseTermColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            setCellFactoryForStringColumn(courseTermColumn);

            courseGradeColumn.setCellValueFactory(cellData -> {
                Course course = cellData.getValue();
                if (course.getStudentCourseDetails() != null) {
                    return new SimpleStringProperty(course.getStudentCourseDetails().getCourseGrade());
                } else {
                    return new SimpleStringProperty("");
                }
            });
            courseGradeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            setCellFactoryForStringColumn(courseGradeColumn);

            table.setEditable(true);
            table.setItems(courses);
        }
        catch (Exception e){
            e.printStackTrace();
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


    private void setCellFactoryForStringColumn(TableColumn<Course, String> column) {
        column.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int rowIndex = event.getTablePosition().getRow();
            Course editedCourse = column.getTableView().getItems().get(rowIndex);
            if (editedCourse.getStudentCourseDetails() != null) {
                if (column.getText().equals("Course Notes")) {
                    editedCourse.getStudentCourseDetails().setCourseNotes(newValue);
                } else if (column.getText().equals("Course Term")) {
                    editedCourse.getStudentCourseDetails().setCourseTerm(newValue);
                } else if (column.getText().equals("Course Grade")) {
                    editedCourse.getStudentCourseDetails().setCourseGrade(newValue);
                }
            }
        });
    }

    //Goes through each course, if null in all it won't insert, if anything is not null it will insert it into the database.
    public void modifyStudentCourseDetails(ObservableList<Course> courseList) {
        for (Course originalCourse : courseList) {
            System.out.println(originalCourse);

            StudentCourseDetails details = originalCourse.getStudentCourseDetails();
            if (details == null || (details.getCourseTerm() == null && details.getCourseGrade() == null && details.getCourseNotes() == null && !details.isCourseCompletion())) {
                System.out.println("Skipping null details for course: " + originalCourse.getCourseID());
                continue;
            }
            System.out.println("Processing course: " + originalCourse.getCourseID());

            String courseId = originalCourse.getCourseID();

            boolean recordExists = DataCalls.doesStudentCourseDetailsExist(studentId, courseId);

            if (!recordExists) {
                DataCalls.insertStudentCourseDetails(studentId, courseId, details);
                System.out.println("Record does not exist, inserted");
            } else {
                updateStudentCourseDetails(studentId, courseId, details);
                System.out.println("Updating existing record");
            }
        }
    }
}


