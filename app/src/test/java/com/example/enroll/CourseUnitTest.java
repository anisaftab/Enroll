package com.example.enroll;

import static org.junit.Assert.assertEquals;

import com.example.enroll.ui.login.Course;

import org.junit.Test;

public class CourseUnitTest {

    private final Course mockCourse = new Course("Intro to Software", "SEG2105");

    String actualValue;
    String expectedValue;

    @Test
    public void evaluateGetCourseName(){
        actualValue = mockCourse.getCourseName();
        expectedValue = "Intro to Software";
        assertEquals(".getCourseName not working", actualValue,expectedValue);
    }
    @Test
    public void evaluateGetCourseCode(){
        actualValue = mockCourse.getCourseCode();
        expectedValue = "SEG2105";
        assertEquals(".getCourseCode not working", actualValue,expectedValue);
    }
    @Test
    public void evaluateGetCourseCapacity(){
        final int capacity = 13;
        mockCourse.setCourseCapacity(capacity);
        int actualValue = mockCourse.getCourseCapacity();
        int expectedValue = capacity;
        assertEquals(".getCourseCapacity not working", actualValue,expectedValue);
    }
    @Test
    public void evaluateGetCourseDescription(){
        final String des = "Best Course Ever";
        mockCourse.setCourseDescription(des);
        actualValue = mockCourse.getCourseDescription();
        expectedValue = des;
        assertEquals(".getCourseDescription not working", actualValue,expectedValue);
    }

    @Test
    public void evaluateSetCourseName(){
        final String newName = "Intro to SEG";
        mockCourse.setCourseName(newName);

        actualValue = mockCourse.getCourseName();
        expectedValue = newName;

        assertEquals(".setCourseName not working", actualValue,expectedValue);

    }



}
