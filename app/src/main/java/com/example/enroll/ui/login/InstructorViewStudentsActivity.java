package com.example.enroll.ui.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enroll.R;

import java.util.ArrayList;

public class InstructorViewStudentsActivity extends AppCompatActivity {
    ListView studentListView;
    ArrayList<String> studentList;
    ArrayAdapter adapter;
    Button back_button;

    MyDBHandler db = new MyDBHandler(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_students);

        back_button = (Button) findViewById(R.id.back_button_instructor);

        studentList = new ArrayList<>();
        studentListView = findViewById(R.id.students_enrolled_in_course);

        String user = "";
        String name = "";
        String courseID = "";
        String courseName = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user = extras.getString("user");
            name = extras.getString("name");
            courseID = extras.getString("courseID");
            courseName = extras.getString("courseName");
        }

        viewStudents(courseID);

        String finalUser = user;
        String finalName = name;
        String finalCourseID = courseID;
        String finalCourseName = courseName;
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), InstructorEditCourseActivity.class);
                myIntent.putExtra("user", finalUser);
                myIntent.putExtra("name", finalName);
                myIntent.putExtra("courseID", finalCourseID);
                myIntent.putExtra("courseName", finalCourseName);
                startActivity(myIntent);
            }
        });
    }

    private void viewStudents(String courseID){
        studentList.clear();
        ArrayList<String> students = db.getStudentsInCourse(courseID);


        if (students.size() == 0) {
            Toast.makeText(InstructorViewStudentsActivity.this, "Error. No students in course.", Toast.LENGTH_SHORT).show();
        } else{
            studentList.addAll(students);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
