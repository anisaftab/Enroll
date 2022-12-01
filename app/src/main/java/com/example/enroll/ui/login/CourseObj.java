package com.example.enroll.ui.login;

public class CourseObj {

    public String course_code, course_name, course_instructor_name, day1, day2, time1, time2, course_description, course_capacity, course_current_capacity;

    public CourseObj(String course_code, String course_name, String course_instructor_name,
                     String day1, String day2, String time1, String time2, String course_description,
                     String course_capacity, String course_current_capacity){
        this.course_code = course_code;
        this.course_name = course_name;
        this.course_instructor_name = course_instructor_name;
        this.day1 = day1;
        this.day2 = day2;
        this.time1 = time1;
        this.time2 = time2;
        this.course_description = course_description;
        this.course_capacity = course_capacity;
        this.course_current_capacity = course_current_capacity;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_instructor_name() {
        return course_instructor_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getCourse_capacity() {
        return course_capacity;
    }

    public String getCourse_current_capacity() {
        return course_current_capacity;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getDay1() {
        return day1;
    }

    public String getTime1() {
        return time1;
    }

    public String getDay2() {
        return day2;
    }

    public String getTime2() {
        return time2;
    }
}
