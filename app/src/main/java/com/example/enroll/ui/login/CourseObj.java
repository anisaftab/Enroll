package com.example.enroll.ui.login;

public class CourseObj {

    public String course_code, day1, time1, day2, time2;

    public CourseObj(String course_code, String day1, String time1, String day2, String time2){
        this.course_code = course_code;
        this.day1 = day1;
        this.time1 = time1;
        this.day2 = day2;
        this.time2 = time2;
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
