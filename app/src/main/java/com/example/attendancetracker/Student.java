package com.example.attendancetracker;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable {
    private String name;
    private String id;
    private Set<String> presentDates; // Stores dates this student was present

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.presentDates = new HashSet<>();
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public void setPresent(String date) { presentDates.add(date); }
    public boolean isPresent(String date) { return presentDates.contains(date); }
    public int getPresentCount() { return presentDates.size(); }
}