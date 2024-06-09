package com.example.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.example.domain.Course;
import com.example.servicesz.CourseRepository;

public class MemCourseRepo implements CourseRepository {

    private static long nextNumber = 0;
    private Map<String, Course> repo = new HashMap<>();
   
    @Override
    public String showAllCourse() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        for (var v : repo.values()) {
            sj.add(v.toString());
        }
        return sj.toString();
    }


    @Override
    public Course addCourse(String courseName) {
        String courseNumber = "L_10" + ++nextNumber;
        Course c = new Course(courseNumber, courseName);
        if (repo.putIfAbsent(courseNumber, c) == null) {
            return c;
        }
        ;
        return null;
    }

    @Override
    public Course removeCourse(String courseNumber) {
        return repo.remove(courseNumber);
    }


    @Override
    public Course findCourse(String courseNumber) {
        return repo.get(courseNumber);
    }

    @Override
    public String getCourseNumber(String courseNumber) {
        return repo.get(courseNumber).getCourseNumber();
    }

    @Override
    public String getCourseName(String courseNumber) {
        return repo.get(courseNumber).getName();

    }

}
