package com.example.attendancetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course implements Serializable {
    // Metadata
    public String department;
    public String session;
    public String courseName;
    public String teacherName;
    public String courseCode;

    // Data
    private List<Student> students;
    private Set<String> datesClassesHeld;

    public Course(String department, String session, String courseName, String teacherName, String courseCode) {
        this.department = department;
        this.session = session;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.courseCode = courseCode;
        this.students = new ArrayList<>();
        this.datesClassesHeld = new HashSet<>();
    }

    public List<Student> getStudents() { return students; }

    public void addStudent(Student s) { students.add(s); }

    public void addClassDate(String date) { datesClassesHeld.add(date); }

    public int getTotalClasses() { return datesClassesHeld.size(); }
}