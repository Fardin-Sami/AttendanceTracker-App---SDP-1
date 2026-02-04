package com.example.attendancetracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Sami", "101");
    }

    @Test
    public void testStudentCreation() {
        assertEquals("Sami", student.getName());
        assertEquals("101", student.getId());
        assertEquals(0, student.getPresentCount());
    }

    @Test
    public void testMarkAttendance_Success() {
        student.setPresent("12/10");

        assertTrue(student.isPresent("12/10"), "Student should be marked present.");

        assertEquals(1, student.getPresentCount(), "Days present should be 1.");
    }

    @Test
    public void testMarkAttendance_Duplicate() {
        student.setPresent("12/10");
        student.setPresent("12/10");

        assertEquals(1, student.getPresentCount(), "Count should not increase for duplicate entry.");
    }

    @Test
    public void testMarkAttendance_MultipleDates() {
        student.setPresent("12/10");
        student.setPresent("12/11");

        assertEquals(2, student.getPresentCount());
    }
}