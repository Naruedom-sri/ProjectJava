package com.example.servicesz;

import com.example.domain.Course;

public interface CourseRepository {

    public String showAllCourse();

    public Course addCourse(String courseName);

    public Course removeCourse(String courseNumber);

    public String getCourseNumber(String courseNumber);

    public String getCourseName(String courseNumber);

    public Course findCourse(String courseNumber);




}
