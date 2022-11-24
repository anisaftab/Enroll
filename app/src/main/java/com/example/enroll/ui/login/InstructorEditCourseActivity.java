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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enroll.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class InstructorEditCourseActivity extends AppCompatActivity {

    Spinner day1, day2, hours1, hours2;
    Button updateCourseDaysTime, updateCourseCapacity, updateCourseDescription;
    EditText courseCapacity, courseDescription;

    MyDBHandler db = new MyDBHandler(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_course);

        day1 = (Spinner) findViewById(R.id.course_day1);
        day2 = (Spinner) findViewById(R.id.course_day2);
        hours1 = (Spinner) findViewById(R.id.course_time1);
        hours2 = (Spinner) findViewById(R.id.course_time2);
        updateCourseDaysTime = (Button) findViewById(R.id.update_course_times);
        updateCourseCapacity = (Button) findViewById(R.id.update_course_capacity);
        updateCourseDescription = (Button) findViewById(R.id.update_course_description);
        courseCapacity = findViewById(R.id.course_capacity);
        courseDescription = findViewById(R.id.course_description);

        String[] days1 = new String[]{"Monday", "Tuesday"};
        String[] days2 = new String[]{"Wednesday", "Thursday", "Friday"};
        String[] hours = new String[]{"8am-10am", "10am-12pm", "12pm-2pm", "2pm-4pm", "4pm-6pm", "6pm-8pm", "8pm-10pm"};

        ArrayAdapter<String> adapterDays1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days1);
        ArrayAdapter<String> adapterDays2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days2);
        ArrayAdapter<String> adapterHours = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hours);

        day1.setAdapter(adapterDays1);
        day2.setAdapter(adapterDays2);
        hours1.setAdapter(adapterHours);
        hours2.setAdapter(adapterHours);

        final String[] chosenDay1 = new String[1];
        final String[] chosenDay2 = new String[1];
        final String[] chosenHour1 = new String[1];
        final String[] chosenHour2 = new String[1];

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

        String finalUser = user;
        String finalName = name;
        String finalCourseID = courseID;
        String finalCourseName = courseName;


        day1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDay1[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        day2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDay2[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hours1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenHour1[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hours2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenHour2[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateCourseDaysTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean successful = false;

                successful = db.updateCourseTimes(finalCourseID, finalCourseName,
                        chosenDay1[0], chosenDay2[0], chosenHour1[0], chosenHour2[0]);

                if(successful){
                    Toast.makeText(InstructorEditCourseActivity.this, "Course days/times updated successfully.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(InstructorEditCourseActivity.this, "Course days/time NOT updated.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        updateCourseCapacity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean successful = false;

                int newCapacity = Integer.parseInt(courseCapacity.getText().toString());

                if(newCapacity < 1){
                    Toast.makeText(InstructorEditCourseActivity.this, "Invalid number.", Toast.LENGTH_SHORT).show();
                }else{
                    successful = db.updateCourseCapacity(finalCourseID, finalCourseName, newCapacity);

                    if(successful){
                        Toast.makeText(InstructorEditCourseActivity.this, "Course capacity updated successfully.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(InstructorEditCourseActivity.this, "Course capacity NOT updated.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        updateCourseDescription.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean successful = false;

                String newDescription = (courseDescription.getText().toString());

                if(newDescription.equals("")){
                    Toast.makeText(InstructorEditCourseActivity.this, "Invalid description", Toast.LENGTH_SHORT).show();
                }else{
                    successful = db.updateCourseDescription(finalCourseID, finalCourseName, newDescription);

                    if(successful){
                        Toast.makeText(InstructorEditCourseActivity.this, "Course description updated successfully.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(InstructorEditCourseActivity.this, "Course description NOT updated.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }
}
