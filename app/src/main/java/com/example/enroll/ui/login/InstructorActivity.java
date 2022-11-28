package com.example.enroll.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enroll.R;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {
    EditText course_name, course_id;
    Button findCourse, editCourse, assignToCourse, removeFromCourse;
    ListView courseListView;
    MyDBHandler db = new MyDBHandler(this);
    ArrayAdapter adapter;

    ArrayList<String> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        courseList = new ArrayList<>();

        course_name = (EditText) findViewById(R.id.course_name_instructor);
        course_id = (EditText) findViewById(R.id.course_id_instructor);
        findCourse = (Button) findViewById(R.id.find_course_instructor);
        editCourse = (Button) findViewById(R.id.edit_your_course);
        assignToCourse = (Button) findViewById(R.id.assign_instructor_to_course);
        removeFromCourse = (Button) findViewById(R.id.remove_instructor_from_course);
        courseListView = findViewById(R.id.courseListView);

        String user = "";
        String name = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user = extras.getString("user");
            name = extras.getString("name");
        }

        String finalUser = user;
        String finalName = name;

        viewCourse();

        editCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(getApplicationContext(), InstructorEditCourseActivity.class);
                myIntent.putExtra("user", finalUser);
                myIntent.putExtra("name", finalName);

                String courseID = course_id.getText().toString();
                String courseName = course_name.getText().toString();

                if(courseID.equals("") || courseName.equals("")){
                    Toast.makeText(InstructorActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                if(courseID.equals("") || courseName.equals("")){
                    Toast.makeText(InstructorActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if(db.getInstructorUsername(courseID, courseName) == null){
                    Toast.makeText(InstructorActivity.this, "You are not the instructor.", Toast.LENGTH_SHORT).show();
                } else if(!db.getInstructorUsername(courseID, courseName).equals(finalUser)){
                    Toast.makeText(InstructorActivity.this, "You are not the instructor.", Toast.LENGTH_SHORT).show();
                } else{
                    myIntent.putExtra("courseID", courseID);
                    myIntent.putExtra("courseName", courseName);
                    startActivity(myIntent);
                }

            }
        });

        findCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                viewCourse();

                String courseID = course_id.getText().toString();
                String courseName = course_name.getText().toString();

                Cursor res = null;

                try {
                        res = db.findCourse(courseID, courseName);
                } catch (SQLException e){
                        e.printStackTrace();
                }

                courseList.clear();

                assert res != null;
                if(res.getCount() == 0){
                    Toast.makeText(InstructorActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                } else {
                    while(res.moveToNext()){
                        String instructorUser = res.getString(2);

                        if(res.getString(2) == null){
                            instructorUser = "No Instructor.";
                        }

                        courseList.add(res.getString(0) + ": " + res.getString(1)+". Instructor: " + instructorUser);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        assignToCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String courseID = course_id.getText().toString();
                String courseName = course_name.getText().toString();

                boolean successful = false;

                successful = db.assignInstructor(courseID, courseName, finalUser, finalName);

                if(successful){
                    Toast.makeText(InstructorActivity.this, "You have been assigned to the course.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(InstructorActivity.this, "Course assignment unsuccessful.", Toast.LENGTH_SHORT).show();
                }

                viewCourse();
            }
        });

        removeFromCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String courseID = course_id.getText().toString();
                String courseName = course_name.getText().toString();

                boolean successful = false;

                successful = db.removeInstructor(courseID, courseName, finalUser, finalName);

                if(successful){
                    Toast.makeText(InstructorActivity.this, "You have been removed from the course.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(InstructorActivity.this, "Course removal unsuccessful.", Toast.LENGTH_SHORT).show();
                }

                viewCourse();
            }
        });
    }

    private void viewCourse(){
        courseList.clear();
        Cursor cursor = db.getCourseData();

        if (cursor.getCount() == 0) {
            Toast.makeText(InstructorActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String instructorUser = cursor.getString(2);

                if(cursor.getString(2) == null){
                    instructorUser = "No Instructor.";
                }

                courseList.add(cursor.getString(0) + ": " +cursor.getString(1)+". Instructor: " + instructorUser);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }
}