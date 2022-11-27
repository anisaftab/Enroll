package com.example.enroll.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.enroll.R;

import java.util.ArrayList;
import java.util.Objects;


public class StudentActivity extends AppCompatActivity {
    Spinner course_day;
    Button find_course, enroll_course, unenroll_course, view_courses;
    EditText course_code, course_name;
    ListView courseListView;
    ArrayList<String> courseList;
    ArrayAdapter adapter;

    MyDBHandler db = new MyDBHandler(this);

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        courseList = new ArrayList<>();

        course_day = (Spinner) (findViewById(R.id.course_day_student));
        find_course = (Button) findViewById(R.id.find_course_student);
        enroll_course = (Button) findViewById(R.id.enroll_student);
        unenroll_course = (Button) findViewById(R.id.unenroll_student);
        course_code = findViewById(R.id.course_id_student);
        course_name = findViewById(R.id.course_name_student);
        courseListView = findViewById(R.id.studentCourseListView);
        view_courses = (Button) findViewById(R.id.view_enrolled_courses);

        String[] days = new String[]{"--","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);

        course_day.setAdapter(adapterDays);
        final String[] chosenDay = new String[1];

        String user = "";
        String name = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user = extras.getString("user");
            name = extras.getString("name");
        }

        viewCourse();

        course_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDay[0] = (String) adapterView.getItemAtPosition(i);

                if(Objects.equals(chosenDay[0], "--")){
                    chosenDay[0] = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        find_course.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                viewCourse();

                String courseCode = course_code.getText().toString();
                String courseName = course_name.getText().toString();

                Cursor res = null;

                try {
                    if(Objects.equals(chosenDay[0], "--")){
                        chosenDay[0] = "";
                    }
                    res = db.findStudentCourse(courseCode, courseName, chosenDay[0]);
                } catch (SQLException e){
                    e.printStackTrace();
                }

                courseList.clear();

                assert res != null;
                if(res.getCount() == 0){
                    Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                } else {
                    while(res.moveToNext()){
                        courseList.add(res.getString(0) + ": " + res.getString(1)+". Capacity: " + res.getString(10) +"/" + res.getString(9));
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        String finalUser = user;
        enroll_course.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String courseCode = course_code.getText().toString();
                String courseName = course_name.getText().toString();

                boolean successful = false;


                successful = db.registerStudentForCourse(courseCode, courseName, finalUser);

                if(successful){
                    Toast.makeText(StudentActivity.this, "You have been enrolled in the course!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(StudentActivity.this, "Course enrollment unsuccessful.", Toast.LENGTH_SHORT).show();
                }

                viewCourse();
            }
        });

        unenroll_course.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String courseCode = course_code.getText().toString();
                String courseName = course_name.getText().toString();

                boolean successful = false;

                successful = db.unenrollStudent(courseCode, courseName, finalUser);

                if(successful){
                    Toast.makeText(StudentActivity.this, "You have been unenrolled from the course!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(StudentActivity.this, "Course unenrollment unsuccessful.", Toast.LENGTH_SHORT).show();
                }

                viewCourse();
            }
        });

        String finalName = name;
        view_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), StudentEnrolledCoursesActivity.class);
                myIntent.putExtra("user", finalUser);
                myIntent.putExtra("name", finalName);
                startActivity(myIntent);
            }
        });

    }

    private void viewCourse(){
        courseList.clear();
        Cursor cursor = db.getCourseData();

        if (cursor.getCount() == 0) {
            Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + ": " +cursor.getString(1)+". Capacity: " + cursor.getString(10) +"/" + cursor.getString(9));
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }
}
