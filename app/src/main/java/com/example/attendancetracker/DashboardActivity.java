package com.example.attendancetracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    AttendanceManager manager;
    String currentFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        currentFileName = getIntent().getStringExtra("FILE_NAME");

        manager = new AttendanceManager();
        boolean loaded = manager.loadCourse(this, currentFileName);

        if (!loaded || manager.getCourse() == null) {
            Toast.makeText(this, "Error loading course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(manager.getCourse().courseName + "\n" + manager.getCourse().courseCode);

        findViewById(R.id.btnAddStudent).setOnClickListener(v -> showAddStudentDialog());

        findViewById(R.id.btnMark).setOnClickListener(v -> {
            Intent intent = new Intent(this, MarkAttendanceActivity.class);
            intent.putExtra("FILE_NAME", currentFileName);
            startActivity(intent);
        });

        findViewById(R.id.btnReport).setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("FILE_NAME", currentFileName);
            startActivity(intent);
        });
    }

    private void showAddStudentDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
        EditText etName = view.findViewById(R.id.etName);
        EditText etId = view.findViewById(R.id.etId);

        new AlertDialog.Builder(this)
                .setTitle("Add New Student")
                .setView(view)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = etName.getText().toString();
                    String id = etId.getText().toString();
                    if (!name.isEmpty() && !id.isEmpty()) {
                        manager.getCourse().addStudent(new Student(name, id));

                        manager.saveCourse(this, currentFileName);

                        Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}