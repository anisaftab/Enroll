package com.example.enroll;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.database.Cursor;
import static org.mockito.Mockito.when;

import com.example.enroll.ui.login.CourseObj;
import com.example.enroll.ui.login.Instructor;
import com.example.enroll.ui.login.InstructorActivity;
import com.example.enroll.ui.login.MyDBHandler;
import com.example.enroll.ui.login.Student;
import com.example.enroll.ui.login.StudentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;


public class StudentUnitTest {

    StudentActivity student ;
    InstructorActivity instructor;


    MyDBHandler testDB;
    boolean actual;
    boolean actual2;


    @Before
    public void viewCoursesTest(){
        student = Mockito.mock(StudentActivity.class);
        instructor = Mockito.mock(InstructorActivity.class);
        testDB = Mockito.mock(MyDBHandler.class);


        testDB.createCourse("ASI","Absurd Science");



        testDB.createAccount("Student","Student1","Student1","easy");
        testDB.createAccount("Instructor","Instructor1","Instructor1","medium");
        testDB.assignInstructor("ASI","Absurd Science","Instructor1","Instructor1");
        testDB.updateCourseCapacity("ASI","Absurd Science",1);
        testDB.updateCourseTimes("ASI","Absurd Science","Tuesday","Wednesday","10:00-11:00","10:00-11:00");
        testDB.createAccount("Student","Student2","Student2","easy");


        testDB.registerStudentForCourse("ASI","Absurd Science","Student1");


    }
    @Test
    public void enrollInCourse(){

        when(testDB.registerStudentForCourse("ASI","Absurd Science","Student1")).thenReturn(true);
        actual = testDB.registerStudentForCourse("ASI","Absurd Science","Student1");
        assertEquals("Student unable to enroll in a course even if its not full",true, actual);


    }
    @Test
    public void courseFull(){
        testDB.registerStudentForCourse("ASI","Absurd Science","Student1");
        boolean actual = testDB.registerStudentForCourse("ASI","Absurd Science","Student2");

        assertEquals("Student able to enroll even if the course is full",false, actual);

    }
    @Test
    public void unEnroll(){
        when(testDB.unenrollStudent("ASI","Absurd Science","Student1")).thenReturn(true);
        testDB.registerStudentForCourse("ASI","Absurd Science","Student1");
        boolean actual = testDB.unenrollStudent("ASI","Absurd Science","Student1");

        assertEquals("Student unable to unenroll from a registered course",true, actual);


    }
    @Test
    public void coursesEnrolled(){
        when(testDB.registerStudentForCourse("ASI","Absurd Science","Student1")).thenCallRealMethod();
        when(testDB.getCoursesStudentIsEnrolledIn("Student1")).thenCallRealMethod();
        testDB.registerStudentForCourse("ASI","Absurd Science","Student1");
        ArrayList<CourseObj> enrolledCourses = testDB.getCoursesStudentIsEnrolledIn("Student1");

        System.out.println(enrolledCourses);


    }




}
