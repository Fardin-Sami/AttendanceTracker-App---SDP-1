package com.example.attendancetracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.List;

public class CourseSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        findViewById(R.id.btnNewCourse).setOnClickListener(v -> {
            startActivity(new Intent(CourseSelectionActivity.this, SetupActivity.class));
        });

        loadList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList() {
        ListView lvCourses = findViewById(R.id.lvCourses);
        AttendanceManager manager = new AttendanceManager();

        List<String> files = manager.getCourseList(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, files);
        lvCourses.setAdapter(adapter);

        lvCourses.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFileName = files.get(position);
            Intent intent = new Intent(CourseSelectionActivity.this, DashboardActivity.class);
            intent.putExtra("FILE_NAME", selectedFileName);
            startActivity(intent);
        });

        lvCourses.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedFileName = files.get(position);
            showDeleteDialog(selectedFileName);
            return true;
        });
    }

    private void showDeleteDialog(String fileName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete " + fileName + "? This cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean deleted = deleteFile(fileName);

                    if (deleted) {
                        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
                        loadList();
                    } else {
                        Toast.makeText(this, "Error deleting file", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}