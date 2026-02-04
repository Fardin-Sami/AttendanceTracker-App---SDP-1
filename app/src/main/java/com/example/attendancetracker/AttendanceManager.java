package com.example.attendancetracker;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {
    private Course course;

    public void createCourse(String dept, String session, String cName, String tName, String cCode) {
        this.course = new Course(dept, session, cName, tName, cCode);
    }

    public Course getCourse() { return course; }

    public void saveCourse(Context context, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(course);
            oos.close();
            fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public boolean loadCourse(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            course = (Course) ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getCourseList(Context context) {
        List<String> courses = new ArrayList<>();
        File dir = context.getFilesDir();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".dat")) {
                    courses.add(file.getName());
                }
            }
        }
        return courses;
    }
}