package com.example.enroll.ui.login;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
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
        } else{
            for(int i = 0; i < enrolledCourses.size(); i++){
                CourseObj course = enrolledCourses.get(i);
                String message = course.getCourse_code() + ": " + course.getCourse_name() + ". Professor: " + course.getCourse_instructor_name()
                        + ". Classes - " + course.getDay1() + ": " + course.getTime1() + " and " + course.getDay2() + ": " + course.getTime2() + ". \n" +
                        "Capacity: " + course.getCourse_current_capacity() + "/" + course.getCourse_capacity() + ". Description: " + course.getCourse_description() + ".";
                courseList.add(message);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        courseList.clear();
//        Cursor cursor = db.getCourseData();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
//        } else {
//            while (cursor.moveToNext()) {
//                courseList.add(cursor.getString(0) + ": " +cursor.getString(1)+". Capacity: " + cursor.getString(10) +"/" + cursor.getString(9));
//            }
//        }
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
//        courseListView.setAdapter(adapter);
    }
}