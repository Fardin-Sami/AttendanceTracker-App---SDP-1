package com.example.attendancetracker;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AttendanceManagerTest {

    private AttendanceManager manager;
    private Context context;
    private static final String TEST_FILE = "test_course.dat";

    @Before
    public void setUp() {
        manager = new AttendanceManager();
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        File file = new File(context.getFilesDir(), TEST_FILE);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testCreateAndAddStudent() {
        manager.createCourse("CSE", "2026", "Java", "Mr. X", "CSE101");

        assertNotNull("Course should be created", manager.getCourse());

        manager.getCourse().addStudent(new Student("Mehedi", "501"));

        assertEquals(1, manager.getCourse().getStudents().size());
        assertEquals("Mehedi", manager.getCourse().getStudents().get(0).getName());
    }

    @Test
    public void testSaveAndLoadCourse() {
        manager.createCourse("CSE", "2026", "Java", "Mr. X", "CSE101");
        manager.getCourse().addStudent(new Student("Sami", "700"));

        manager.saveCourse(context, TEST_FILE);

        AttendanceManager newManager = new AttendanceManager();

        boolean loaded = newManager.loadCourse(context, TEST_FILE);

        assertTrue("File should load successfully", loaded);
        assertNotNull("Loaded course should not be null", newManager.getCourse());
        assertEquals("CSE101", newManager.getCourse().courseCode);
        assertEquals("Sami", newManager.getCourse().getStudents().get(0).getName());
    }
}