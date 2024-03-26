package com.example.databasehelp;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private String courseID;
    private String courseName;
    private int courseCredits;
    private int courseSemester;
    private StudentCourseDetails studentCourseDetails;


    public Course(String courseID, String courseName, int courseCredits, int courseSemester, StudentCourseDetails studentCourseDetails) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
        this.courseSemester = courseSemester;
        this.studentCourseDetails = studentCourseDetails;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID){
        this.courseID = courseID;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getCourseName(){
        return courseName;
    }

    public void setCourseCredits(int courseCredits){
        this.courseCredits = courseCredits;
    }

    public int getCourseCredits(){
        return courseCredits;
    }

    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }
    public int getCourseSemester(){
        return courseSemester;
    }


    public StudentCourseDetails getStudentCourseDetails() {
        return studentCourseDetails;
    }

    public void setStudentCourseDetails(StudentCourseDetails studentCourseDetails) {
        this.studentCourseDetails = studentCourseDetails;
    }
}

