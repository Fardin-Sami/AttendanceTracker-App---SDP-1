package com.example.attendancetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        EditText etDept = findViewById(R.id.etDept);
        EditText etSession = findViewById(R.id.etSession);
        EditText etCName = findViewById(R.id.etCourseName);
        EditText etTeacher = findViewById(R.id.etTeacher);
        EditText etCode = findViewById(R.id.etCode);
        Button btnCreate = findViewById(R.id.btnCreate);

        AttendanceManager manager = new AttendanceManager();

        btnCreate.setOnClickListener(v -> {
            String code = etCode.getText().toString().trim();
            if (code.isEmpty()) {
                etCode.setError("Required");
                return;
            }

            manager.createCourse(
                    etDept.getText().toString(),
                    etSession.getText().toString(),
                    etCName.getText().toString(),
                    etTeacher.getText().toString(),
                    code
            );

            String fileName = code + ".dat";
            manager.saveCourse(this, fileName);

            Toast.makeText(this, "Course Created!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}