package com.example.databasehelp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.*;

import static java.sql.DriverManager.getConnection;
import static java.sql.DriverManager.println;

public class DataCalls {

    public static String URL = "jdbc:mysql://localhost:3306/STUDENT_MAJOR_SHEETS";
    static String Username = "root";
    static String Password = "password";



    public static boolean doesStudentExist(int studentId) {
        String query = "SELECT Student_ID FROM student WHERE Student_ID = ?";
        try (Connection connection = getConnection(URL, Username, Password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Grabs course data by semester
    public static ObservableList<Course> selectCoursesBySemester(int studentId, int courseSemester) {
        String sql = "SELECT c.course_id, c.course_name, c.course_credits, s.course_grade, s.course_term, s.course_notes, s.course_completion\n" +
                "FROM course c\n" +
                " LEFT JOIN StudentCourseDetails s ON c.course_id = s.course_id AND s.Student_ID = ?\n" +
                "WHERE c.course_semester = ? AND (s.course_id IS NOT NULL OR s.course_id IS NULL);";

        ObservableList<Course> courses = FXCollections.observableArrayList();

        try (Connection connection = getConnection(URL, Username, Password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseSemester);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String courseId = resultSet.getString("course_id");
                    String courseName = resultSet.getString("course_name");
                    int courseCredits = resultSet.getInt("course_credits");

                    String courseGrade = resultSet.getString("course_grade");
                    String courseTerm = resultSet.getString("course_term");
                    String courseNotes = resultSet.getString("course_notes");
                    boolean courseCompletion = resultSet.getBoolean("course_completion");


                    // Create Course and StudentCourseDetails objects
                    StudentCourseDetails studentCourseDetails = new StudentCourseDetails(studentId, courseGrade, courseTerm, courseNotes, courseCompletion, courseId);
                    Course course = new Course(courseId, courseName, courseCredits, courseSemester, studentCourseDetails);

                    // Set StudentCourseDetails object to the Course
                    course.setStudentCourseDetails(studentCourseDetails);

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

        return courses;
    }
    // Inserts student data
    public static void insertStudent(int student_id, String student_FullName, String student_phone_number,
                                     String student_email, String student_address, int major_id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(URL, Username, Password)) {
                String sql = "INSERT INTO Student (Student_ID, Student_FullName, Student_Phone_Number, " +
                        "Student_Email, Student_Address, Major_ID) VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, student_id);
                    preparedStatement.setString(2, student_FullName);
                    preparedStatement.setString(3, student_phone_number);
                    preparedStatement.setString(4, student_email);
                    preparedStatement.setString(5, student_address);
                    preparedStatement.setInt(6, major_id);

                    preparedStatement.executeUpdate();

                    System.out.println("Student information inserted successfully.");
                } catch (SQLException e) {
                    // Handle or log the SQL exception appropriately
                    e.printStackTrace();
                    throw new RuntimeException("Error executing SQL statement.", e);
                }
            } catch (SQLException e) {
                // Handle or log the SQL exception appropriately
                e.printStackTrace();
                System.err.println("Error establishing database connection.");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


//  selects student info
    public static void selectStudentInfo(int studentId) {
        try (Connection connection = DriverManager.getConnection(URL, Username, Password)) {

            String sql = "SELECT * FROM Student WHERE Student_ID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int studentID = resultSet.getInt("Student_ID");
                    String studentFullName = resultSet.getString("Student_FullName");
                    String studentPhoneNumber = resultSet.getString("Student_Phone_Number");
                    String studentEmail = resultSet.getString("Student_Email");
                    String studentAddress = resultSet.getString("Student_Address");
                    int majorID = resultSet.getInt("Major_ID");

                    System.out.println("Student ID: " + studentID);
                    System.out.println("Full Name: " + studentFullName);
                    System.out.println("Phone Number: " + studentPhoneNumber);
                    System.out.println("Email: " + studentEmail);
                    System.out.println("Address: " + studentAddress);
                    System.out.println("Major ID: " + majorID);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error selecting student information from the database.");
        }
    }

    //grabs each course student specific details.
    public static StudentCourseDetails getStudentCourseDetails(int studentId, String courseId) {
        boolean booleanCompletion;
        String query = "SELECT course_grade, course_term, course_notes, course_completion " +
                "FROM StudentCourseDetails " +
                "WHERE Student_ID = ? AND Course_ID = ?";

        try (Connection connection = getConnection(URL, Username, Password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String courseGrade = resultSet.getString("course_grade");
                    String courseTerm = resultSet.getString("course_term");
                    String courseNotes = resultSet.getString("course_notes");
                    int courseCompletion = resultSet.getInt("course_completion");
                    if (courseCompletion == 1){
                        booleanCompletion = true;
                    }
                    else {
                        booleanCompletion = false;
                    }
                    // Create and return a StudentCourseDetails object
                    return new StudentCourseDetails(studentId, courseGrade, courseTerm, courseNotes, booleanCompletion, courseId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no matching record is found
    }
    public static void updateStudentCourseDetails(int studentId, String courseId, StudentCourseDetails studentCourseDetails) throws SQLException {
        int booleanNumberValue;
        try (Connection connection = DriverManager.getConnection(URL, Username, Password)) {
            String updateQuery = "UPDATE StudentCourseDetails " +
                    "SET course_Notes = ?, course_Term = ?, course_Grade = ?, Course_Completion = ? " +
                    "WHERE Student_ID = ? AND Course_ID = ?";

            if (studentCourseDetails.isCourseCompletion()) {
                booleanNumberValue = 1;
                System.out.println("got to true iscoursecompletion in update call");

            } else {
                booleanNumberValue = 0;
                System.out.println("got to false iscoursecompletion in update call");

            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, studentCourseDetails.getCourseNotes());
                preparedStatement.setString(2, studentCourseDetails.getCourseTerm());
                preparedStatement.setString(3, studentCourseDetails.getCourseGrade());
                preparedStatement.setInt(4, booleanNumberValue);
                preparedStatement.setInt(5, studentId);
                preparedStatement.setString(6, courseId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean doesStudentCourseDetailsExist(int studentId, String courseId) {
        try (Connection connection = getConnection(URL, Username, Password);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM StudentCourseDetails WHERE Student_ID = ? AND Course_ID = ?")) {

            System.out.println("doesstudentcoursedetails GOT HERE");

            statement.setInt(1, studentId);
            statement.setString(2, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertStudentCourseDetails(int studentId, String courseId, StudentCourseDetails studentCourseDetails) throws SQLException {


        try (Connection connection = getConnection(URL, Username, Password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO StudentCourseDetails(Student_ID, Course_ID, Course_Notes, Course_Term, Course_Grade, Course_Completion) VALUES (?, ?, ?, ?, ?, ?)")) {
            System.out.println("INSERTSTUDENTCOURSEDETAILS GOT HERE");
            if (studentCourseDetails.isCourseCompletion()) {
                System.out.println("got to true iscoursecompletion in update call");


                try (PreparedStatement preparedStatement = statement) {
                    preparedStatement.setInt(1, studentId);
                    preparedStatement.setString(2, courseId);
                    preparedStatement.setString(3, studentCourseDetails.getCourseNotes());
                    preparedStatement.setString(4, studentCourseDetails.getCourseTerm());
                    preparedStatement.setString(5, studentCourseDetails.getCourseGrade());
                    preparedStatement.setInt(6, 1);


                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("got to false iscoursecompletion in update call");

            }
        }
    }
    public static ObservableList<Course> selectCoursesByStudent(int studentId) {
        ObservableList<Course> courseList = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(URL, Username, Password)) {
            String query = "SELECT c.* FROM course c INNER JOIN studentcoursedetails scd ON c.course_id = scd.course_id WHERE scd.student_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                int courseCredits = resultSet.getInt("course_credits");
                int semester = resultSet.getInt("course_semester");

                // Retrieve student course details for the current course
                StudentCourseDetails studentCourseDetails = getStudentCourseDetails(studentId, courseId);

                // Create a new Course object only if student course details exist
                if (studentCourseDetails != null) {
                    Course course = new Course(courseId, courseName, courseCredits, semester, studentCourseDetails);
                    courseList.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }

}
