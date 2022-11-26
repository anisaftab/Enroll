package com.example.enroll.ui.login;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enroll.R;

import java.util.ArrayList;

public class StudentEnrolledCoursesActivity extends AppCompatActivity {
    ListView courseListView;
    ArrayList<String> courseList;
    ArrayAdapter adapter;


    MyDBHandler db = new MyDBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrolled_courses);

        courseList = new ArrayList<>();
        courseListView = findViewById(R.id.student_enrolled_courses);

        String user = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user = extras.getString("user");

        }


        viewCourses(user);

    }
    private void viewCourses(String userName){
        courseList.clear();
        ArrayList<CourseObj> enrolledCourses = db.getCoursesStudentIsEnrolledIn(userName);

        if (enrolledCourses.size() == 0) {
            Toast.makeText(StudentEnrolledCoursesActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enrolledCourses);
        courseListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}