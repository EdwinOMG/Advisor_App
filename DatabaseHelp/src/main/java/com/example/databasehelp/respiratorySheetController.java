package com.example.databasehelp;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import static com.example.databasehelp.DataCalls.updateStudentCourseDetails;

public class respiratorySheetController {
    @FXML
    private ImageView imageView1;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private transient TableView<Course> pre_table;
    @FXML
    private transient TableView<Course> first_table;
    @FXML
    private transient TableView<Course> second_table;
    @FXML
    private transient TableView<Course> third_table;
    @FXML
    private transient TableView<Course> fourth_table;

    @FXML
    private transient TableView<Course> sixth_table;
    @FXML
    private TableColumn<Course, String> course_completion_1;
    @FXML
    private TableColumn<Course, String> course_id_1;
    @FXML
    private TableColumn<Course, String> course_name_1;
    @FXML
    private TableColumn<Course, Integer> course_credits_1;
    @FXML
    private TableColumn<Course, String> course_term_1;
    @FXML
    private TableColumn<Course, String> course_grade_1;
    @FXML
    private TableColumn<Course, String> course_notes_1;

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

    @FXML
    private TableColumn<Course, Boolean> course_completion_6;
    @FXML
    private TableColumn<Course, String> course_id_6;
    @FXML
    private TableColumn<Course, String> course_name_6;
    @FXML
    private TableColumn<Course, Integer> course_credits_6;
    @FXML
    private TableColumn<Course, String> course_term_6;
    @FXML
    private TableColumn<Course, String> course_grade_6;
    @FXML
    private TableColumn<Course, String> course_notes_6;

    private int studentId;

    @FXML
    private Label idLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    public static String URL = "jdbc:mysql://localhost:3306/STUDENT_MAJOR_SHEETS";
    static String Username = "root";
    static String Password = "password";

    CourseRequirementManager courseRequirementManager = new CourseRequirementManager();


    public void setStudentId(int studentId) throws SQLException {
        // allows studentid to get set from maincontroller
        this.studentId = studentId;
        System.out.println("Received studentId in AdvisorSheetController: " + studentId);
        loadStudentDetails(studentId);

    }


    public void setStudentCourseDetails(ObservableList<Course> studentCourseDetailsList) {
        //Loads studentcoursedetails based on semester and course
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
                case 5:
                    targetTable = pre_table;
                    break;
                case 6:
                    targetTable = sixth_table;
                    break;
                default:
                    targetTable = null;
                    break;
            }

            if (targetTable != null) {
                replaceCourseInTable(targetTable, course);
            }
        }
    }

    private void replaceCourseInTable(TableView<Course> table, Course newCourse) {
        ObservableList<Course> items = table.getItems();
        int index = -1;

        // Find the index of the old course
        for (int i = 0; i < items.size(); i++) {
            Course oldCourse = items.get(i);
            if (oldCourse.getCourseID().equals(newCourse.getCourseID())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            // Remove the old course from the list
            items.remove(index);
            // Insert the new course at the same index
            items.add(index, newCourse);
        } else {
            // If the old course wasn't found, add the new course to the end of the list
            items.add(newCourse);
        }
    }

    // setter for courserequirementmanager
    public void setStudentDetails(int studentId, ObservableList<Course> studentCourseDetailsList) {
        // Set student ID
        courseRequirementManager.setStudentId(studentId);
        // Set student course details
        courseRequirementManager.setStudentCourseDetails(studentId, studentCourseDetailsList);
    }

    @FXML
    private void initialize() throws SQLException {

        TermGradeColumnMaker termGradeColumnMaker = new TermGradeColumnMaker();
        //Load images
        Image image1 = new Image(getClass().getResource("/Images/respiratoryCare.png").toExternalForm());

        // Set images to ImageViews
        imageView1.setImage(image1);
        loadStudentDetails(studentId);

        //grabbing all the classes in semesters, adding them to observable list for the tables
        ObservableList<Course> semester1Courses = DataCalls.selectCoursesBySemester(studentId, 1, 2);
        ObservableList<Course> coursesList = FXCollections.observableArrayList(semester1Courses);

        ObservableList<Course> semester2Courses = DataCalls.selectCoursesBySemester(studentId, 2, 2);
        ObservableList<Course> semester2List = FXCollections.observableArrayList(semester2Courses);

        ObservableList<Course> semester3Courses = DataCalls.selectCoursesBySemester(studentId, 3, 2);
        ObservableList<Course> semester3List = FXCollections.observableArrayList(semester3Courses);

        ObservableList<Course> semester4Courses = DataCalls.selectCoursesBySemester(studentId, 4, 2);
        ObservableList<Course> semester4List = FXCollections.observableArrayList(semester4Courses);

        ObservableList<Course> preReqCourses = DataCalls.selectCoursesBySemester(studentId, 5, 2);
        ObservableList<Course> preReqList = FXCollections.observableArrayList(preReqCourses);

        ObservableList<Course> semester5Courses = DataCalls.selectCoursesBySemester(studentId, 6, 2);
        ObservableList<Course> semester5List = FXCollections.observableArrayList(semester5Courses);

// Set up tables and columns for each semester
        for (int semester = 1; semester <= 6; semester++) {
            TableView<Course> table = null;
            ObservableList<Course> semesterCourses = null;

            switch (semester) {
                case 1:
                    table = first_table;
                    semesterCourses = semester1Courses;
                    break;
                case 2:
                    table = second_table;
                    semesterCourses = semester2Courses;
                    break;
                case 3:
                    table = third_table;
                    semesterCourses = semester3Courses;
                    break;
                case 4:
                    table = fourth_table;
                    semesterCourses = semester4Courses;
                    break;
                case 5:
                    table = pre_table;
                    semesterCourses = preReqCourses;
                    break;
                case 6:
                    table = sixth_table;
                    semesterCourses = semester5Courses;
                    break;
                default:
                    break;
            }

            if (table != null && semesterCourses != null) {
                setupTableColumns(table, semesterCourses, String.valueOf(semester));
            }
        }

        // Initialize completion status columns
        for (int semester = 1; semester <= 6; semester++) {
            TableView<Course> table = null;
            switch (semester) {
                case 1:
                    table = first_table;
                    break;
                case 2:
                    table = second_table;
                    break;
                case 3:
                    table = third_table;
                    break;
                case 4:
                    table = fourth_table;
                    break;
                case 5:
                    table = pre_table;
                    break;
                case 6:
                    table = sixth_table;
                    break;
                default:
                    break;
            }

            if (table != null) {
                courseRequirementManager.initializeCompletionStatusColumn(table, semester);
            }
        }

        // Initialize term and grade columns
        for (int semester = 1; semester <= 6; semester++) {
            TableView<Course> table = null;
            switch (semester) {
                case 1:
                    table = first_table;
                    break;
                case 2:
                    table = second_table;
                    break;
                case 3:
                    table = third_table;
                    break;
                case 4:
                    table = fourth_table;
                    break;
                case 5:
                    table = pre_table;
                    break;
                case 6:
                    table = sixth_table;
                    break;
                default:
                    break;
            }

            if (table != null) {
                TermGradeColumnMaker.initializeTermColumn(table, semester);
                TermGradeColumnMaker.initializeGradeColumn(table, semester);
            }
        }
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
            ObservableList<Course> sixthCourses = sixth_table.getItems();

            modifyStudentCourseDetails(allCourses);
            modifyStudentCourseDetails(secondCourses);
            modifyStudentCourseDetails(thirdCourses);
            modifyStudentCourseDetails(fourthCourses);
            modifyStudentCourseDetails(fifthCourses);
            modifyStudentCourseDetails(sixthCourses);


            System.out.println("Save operation completed for all items in the table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sets up all table columns to the fxml ids
    private void setupTableColumns(TableView<Course> table, ObservableList<Course> courses, String suffix) {
        try {
            TableColumn<Course, String> courseIdColumn = getColumn(table, "course_id_" + suffix);
            TableColumn<Course, String> courseNameColumn = getColumn(table, "course_name_" + suffix);
            TableColumn<Course, Integer> courseCreditsColumn = getColumn(table, "course_credits_" + suffix);
            TableColumn<Course, String> courseNotesColumn = getColumn(table, "course_notes_" + suffix);

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

            table.setEditable(true);
            table.setItems(courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to simplify the column grabbing process.
    private <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String id) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (id.equals(column.getId())) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }

    // Method: Gets the table position and rows new value and puts it into matching studentcoursedetails object
    private void setCellFactoryForStringColumn(TableColumn<Course, String> column) {
        column.setOnEditCommit(event -> {
            System.out.println("CELLFACTORYFORSTRINGCOLUMN: GOT INTO EDIT COMMIT");
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
    public void modifyStudentCourseDetails(ObservableList<Course> courseList) throws SQLException {
        for (Course originalCourse : courseList) {
            StudentCourseDetails details = originalCourse.getStudentCourseDetails();
            if (details == null) {
                System.out.println("Skipping null details for course: " + originalCourse.getCourseID());
                continue;
            }

            String courseId = originalCourse.getCourseID();
            boolean recordExists = DataCalls.doesStudentCourseDetailsExist(studentId, courseId); // Check existence based on both courseId and studentId

            if (!recordExists) {
                DataCalls.insertStudentCourseDetails(studentId, courseId, details);
                System.out.println("Record does not exist, inserted");
            } else {
                updateStudentCourseDetails(studentId, courseId, details);
                System.out.println("Updating existing record");
            }
        }
    }


    // Method to print a node (in this case, the TableView)
    public void loadStudentDetails(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, Username, Password)) {

            String sql = "SELECT * FROM Student WHERE Student_ID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        idLabel.setText(String.valueOf(resultSet.getInt("Student_ID")));
                        nameLabel.setText(resultSet.getString("Student_FullName"));
                        addressLabel.setText(resultSet.getString("Student_Address"));
                        numberLabel.setText(resultSet.getString("Student_Phone_Number"));
                        emailLabel.setText(resultSet.getString("Student_Email"));
                    }

                }
            }
        }
    }
}





