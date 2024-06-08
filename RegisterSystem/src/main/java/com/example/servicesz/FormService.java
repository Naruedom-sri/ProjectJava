package com.example.servicesz;

import java.util.stream.Stream;

import com.example.domain.Course;
import com.example.domain.Student;

public class FormService {
    private StudentRepository student;
    private CourseRepository course;

    public FormService(StudentRepository student, CourseRepository course) {
        this.student = student;
        this.course = course;
    }

    public Student addStudent(String studentName, String year) {
        if (studentName.isBlank() || year.isBlank()) {
            return null;
        }
        return student.addStudent(studentName, year);
    }

    public Student removeStudent(String studentNumber) {
        if (studentNumber.isBlank() || checkStudent(studentNumber) == null) {
            return null;
        }
        return student.removeStudent(studentNumber);
    }

    public String showAllStudent() {
        return student.showAllStudent();
    }

    public Student checkStudent(String studentNumber) {
        if (studentNumber.isBlank()) {
            return null;
        }
        return student.checkStudent(studentNumber);
    }

    public String showAllStudentRemove() {
        return student.showAllStudentRemove();
    }

    public Student returnStudent(String studentNumber) {
        if (checkStudentRemove(studentNumber) != null) {
            return student.returnStudent(studentNumber);
        }
        return null;
    }

    public Student checkStudentRemove(String studentNumber) {
        return student.checkStudentRemove(studentNumber);
    }

    public void sortByStudentNumber() {
        student.getStudents().stream().sorted().forEach(s -> System.out.println(s.toString()));
    }

    public String printHeaderCoureCheck(String coursNumber) {
        return String.format("|          Course          |         %s ", course.getCourseNumber(coursNumber))
                + String.format(": %-41s", course.getCourseName(coursNumber)) + "|";
    }

    public String showAllCourse() {
        return course.showAllCourse();
    }

    public Course findCourse(String courseNumber) {
        if (courseNumber.isBlank()) {
            return null;
        }
        return course.findCourse(courseNumber);
    }

    public Course addCourse(String courseName) {
        return course.addCourse(courseName);
    }

    public Course removeCourse(String courseNumber) {
        if (courseNumber.isBlank() || findCourse(courseNumber) == null) {
            return null;
        }
        return course.removeCourse(courseNumber);
    }

    public boolean registerStudentIntoCourse(String courseNumber, String studentNumber) {
        if (courseNumber.isBlank() || course.findCourse(courseNumber) == null
                || studentNumber.isBlank() || student.checkStudent(studentNumber) == null) {
            return false;
        }
        return student.registerStudentIntoCourse(courseNumber, studentNumber);
    }

    public boolean withdrawStudentIntoCourse(String courseNumber, String studentNumber) {
        if (courseNumber.isBlank() || course.findCourse(courseNumber) == null || studentNumber.isBlank()
                || student.checkStudent(studentNumber) == null) {
            return false;
        }
        return student.withdrawStudentIntoCourse(courseNumber, studentNumber);
    }

    public Stream<Student> getStudentInCourse(String courseNumber) {
        if (courseNumber.isBlank() || course.findCourse(courseNumber) == null) {
            return null;
        }
        return student.getStudentInCourse(courseNumber);
    }

    public void showAllStudentInCourse(String courseNumber) {
        getStudentInCourse(courseNumber).sorted().forEach(c -> System.out.println(c));
    }

}
