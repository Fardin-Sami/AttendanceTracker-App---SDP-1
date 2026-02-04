package com.example.attendancetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarkAttendanceActivity extends AppCompatActivity {
    AttendanceManager manager;
    StudentAdapter adapter;
    EditText etDate;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        fileName = getIntent().getStringExtra("FILE_NAME");

        manager = new AttendanceManager();
        manager.loadCourse(this, fileName);

        etDate = findViewById(R.id.etDate);
        RecyclerView rv = findViewById(R.id.rvStudents);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if (manager.getCourse() != null) {
            adapter = new StudentAdapter(manager.getCourse().getStudents());
            rv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Error loading student list", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String date = etDate.getText().toString();
            if (date.isEmpty()) {
                Toast.makeText(this, "Enter Date!", Toast.LENGTH_SHORT).show();
                return;
            }

            manager.getCourse().addClassDate(date);

            for (String studentId : adapter.checkedIds) {
                for (Student s : manager.getCourse().getStudents()) {
                    if (s.getId().equals(studentId)) {
                        s.setPresent(date);
                    }
                }
            }

            manager.saveCourse(this, fileName);

            Toast.makeText(this, "Attendance Saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.Holder> {
        List<Student> list;
        public Set<String> checkedIds = new HashSet<>();

        public StudentAdapter(List<Student> list) { this.list = list; }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Student s = list.get(position);
            holder.cb.setText(s.getName() + " (" + s.getId() + ")");
            holder.cb.setOnCheckedChangeListener(null);
            holder.cb.setChecked(checkedIds.contains(s.getId()));

            holder.cb.setOnCheckedChangeListener((btn, isChecked) -> {
                if (isChecked) checkedIds.add(s.getId());
                else checkedIds.remove(s.getId());
            });
        }

        @Override
        public int getItemCount() { return list.size(); }

        class Holder extends RecyclerView.ViewHolder {
            CheckBox cb;
            public Holder(View itemView) {
                super(itemView);
                cb = (CheckBox) itemView;
            }
        }
    }
}