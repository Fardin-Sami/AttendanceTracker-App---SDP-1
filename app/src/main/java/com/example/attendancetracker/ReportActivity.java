package com.example.attendancetracker;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        String fileName = getIntent().getStringExtra("FILE_NAME");

        TableLayout table = findViewById(R.id.tableLayout);
        AttendanceManager manager = new AttendanceManager();

        boolean loaded = manager.loadCourse(this, fileName);

        if (!loaded || manager.getCourse() == null) {
            Toast.makeText(this, "Could not load report.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int totalClasses = manager.getCourse().getTotalClasses();

        for (Student s : manager.getCourse().getStudents()) {
            TableRow row = new TableRow(this);
            row.setPadding(5, 10, 5, 10);

            int present = s.getPresentCount();
            int absent = totalClasses - present;
            double percent = (totalClasses > 0) ? ((double)present / totalClasses) * 100 : 0;

            if (percent < 50) row.setBackgroundColor(Color.parseColor("#FFCDD2")); // Red-ish
            else if (percent < 80) row.setBackgroundColor(Color.parseColor("#FFF9C4")); // Yellow-ish
            else row.setBackgroundColor(Color.parseColor("#C8E6C9")); // Green-ish

            addCell(row, s.getName());
            addCell(row, String.valueOf(totalClasses));
            addCell(row, String.valueOf(present));
            addCell(row, String.valueOf(absent));
            addCell(row, String.format("%.1f%%", percent));

            table.addView(row);
        }
    }

    private void addCell(TableRow row, String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(Color.BLACK);
        row.addView(tv);
    }
}